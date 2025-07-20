package com.berlin.aflami.viewmodel.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.selectByGenre
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.uistate.TVShowUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import usecase.ClearSearchHistoryUseCase
import usecase.DeleteQueryFromHistoryUseCase
import usecase.GetRecentHistoryUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.SaveRecentHistoryUseCase

class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTvShowsUseCase,
    private val getRecentHistoryUseCase: GetRecentHistoryUseCase,
    private val saveRecentHistoryUseCase: SaveRecentHistoryUseCase,
    private val deleteQueryFromHistoryUseCase: DeleteQueryFromHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteractionListener,
    FilterInteractionListener {


    private val _recentSearchState = MutableStateFlow<List<String>>(emptyList())
    val recentSearchState = _recentSearchState.asStateFlow()

    init {
        observeSearchKeywordChanges()
        loadRecentSearch()
    }

    private fun loadRecentSearches() {
        startLoading()
        tryToCall(
            call = {
                getRecentHistoryUseCase()
            },
            onSuccess = ::onLoadRecentSearchesSuccess,
            onError = {
                updateState {
                    it.copy(
                        errorMessage = it.errorMessage ?: "Failed to load recent searches",
                        isLoading = false
                    )
                }
            },
        )
    }

    private fun onLoadRecentSearchesSuccess(recentSearches: List<String>) {
        updateState { it.copy(recentSearches = recentSearches, errorMessage = null) }
    }

    private fun onClearAllRecentSearchesSuccess(unit: Unit) {
        updateState { it.copy(recentSearches = emptyList()) }
        return unit
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchKeywordChanges() {
        viewModelScope.launch {
            combine(
                _state.map { it.searchQuery.trim() }.debounce(800).filter { it.isNotEmpty() }
                    .distinctUntilChanged(),
                _state.map { it.selectedTabOption }.distinctUntilChanged(),
                _state.map { it.filterTrigger }.distinctUntilChanged()
            ) { query, _, _ ->
                query
            }.collectLatest {
                onSearchKeywordChanged(it)
                loadRecentSearch()
            }
        }
    }

    private fun onSearchKeywordChanged(query: String) {
        when (state.value.selectedTabOption) {
            TabOption.MOVIES -> fetchMoviesByQuery(query)
            TabOption.TV_SHOWS -> fetchTvShowsByQuery(query)
        }
    }

    private fun fetchTvShowsByQuery(query: String) {
        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20, initialLoadSize = 20
                    ), pagingSourceFactory = {
                        BasePagingSource(
                            call = { page ->
                                searchTvShowsUseCase.invoke(
                                    query = query, page = page
                                )
                            })
                    }).flow
                    .map { it.map { it.toUiState() } }.map { pagingData ->
                    pagingData.filter { tvUiState ->
                        val selectedRating = state.value.filterItemUiState.selectedRating
                        val selectedGenre = state.value.filterItemUiState.selectedGenre
                        val matchesRating =
                            tvUiState.rating.toFloatOrNull()?.let { it > selectedRating } == true
                        val matchesGenre =
                            selectedGenre == null || selectedGenre == GenreType.ALL || tvUiState.genre.any {
                                it == selectedGenre.toGenreType()
                            }
                        matchesGenre && matchesRating
                    }
                }.cachedIn(viewModelScope)
            },
            onSuccess = ::onFetchTvShowsSuccess,
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message, isLoading = false,
                    )
                }
            },
        )
    }

    private fun fetchMoviesByQuery(query: String) {
        updateState { it.copy(isLoading = true) }
        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(
                        pageSize = 10, initialLoadSize = 10
                    ), pagingSourceFactory = {
                        BasePagingSource(
                            call = { page ->
                                searchMoviesUseCase.invoke(
                                    query = query, page = page
                                )
                            })
                    }).flow.map { pagingData -> pagingData.map { it.toUIState() } }
                    .map { pagingData ->
                        pagingData.filter { movieUiState ->
                            val selectedRating = state.value.filterItemUiState.selectedRating
                            val selectedGenre = state.value.filterItemUiState.selectedGenre
                            val matchesRating = movieUiState.rating.toFloatOrNull()
                                ?.let { it > selectedRating } == true
                            val matchesGenre =
                                selectedGenre == null || selectedGenre == GenreType.ALL || movieUiState.genre.any {
                                    it == selectedGenre.toGenreType()
                                }
                            matchesGenre && matchesRating
                        }
                    }.cachedIn(viewModelScope)
            },
            onSuccess = ::onFetchMoviesSuccess,
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false,
                    )
                }
            },
        )
    }

    private fun onFetchTvShowsSuccess(tvShowsFlow: Flow<PagingData<TVShowUiState>>) {
        updateState { it.copy(tvShows = tvShowsFlow, errorMessage = null, isLoading = false) }
    }

    private fun onFetchMoviesSuccess(moviesFlow: Flow<PagingData<MovieUIState>>) {
        updateState { it.copy(movies = moviesFlow, errorMessage = null, isLoading = false) }
    }

    override fun onSearchActionClicked() {
        onSearchQueryChanged(state.value.searchQuery)
        tryToCall(call = {
            saveRecentHistoryUseCase
        }, onSuccess = { result ->
            updateState { it.copy(isLoading = false) }
        }, onError = { error ->
            updateState { it.copy(errorMessage = error.message, isLoading = false) }
        })
    }

    override fun onSearchQueryChanged(query: CharSequence) {
        updateState { it.copy(searchQuery = query.toString(), isLoading = false) }
    }

    override fun onBackClicked() {
        sendNewEffect(SearchUiEffect.NavigatedBack)
    }

    override fun onWorldSearchCardClicked() {
        sendNewEffect(SearchUiEffect.NavigateToWorldSearch)
    }

    override fun onActorSearchCardClicked() {
        sendNewEffect(SearchUiEffect.NavigateToActorSearch)
    }

    private fun startLoading() {
        updateState {
            it.copy(
                isLoading = true,
            )
        }
    }

    override fun onTabOptionClicked(tabOption: TabOption) {
        updateState {
            it.copy(
                isLoading = true,
                selectedTabOption = tabOption,
            )
        }
        onSearchQueryChanged(state.value.searchQuery)
    }

    override fun onCardClicked(id: Int) {
        sendNewEffect(
            SearchUiEffect.NavigatedToMovieDetailsScreen(
                id = id
            )
        )
    }

    override fun onRecentSearchClicked(keyword: String) {
        onSearchQueryChanged(keyword)
        observeSearchKeywordChanges()
    }

    override fun onRecentSearchCleared(keyword: String) {
        updateState { it.copy(isLoading = false) }
        tryToCall(
            call = {
                deleteQueryFromHistoryUseCase(keyword)
            },
            onSuccess = { loadRecentSearches() },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message, isLoading = false, isDialogVisible = false
                    )
                }
            },
        )
    }

    override fun onAllRecentSearchesCleared() {
        updateState { it.copy(isLoading = false) }
        tryToCall(
            call = {
                clearSearchHistoryUseCase
            },
            onSuccess = ::onClearAllRecentSearchesSuccess,
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message, isLoading = false, isDialogVisible = false
                    )
                }
            },
        )
    }

    override fun onFilterButtonClicked() {
        updateState { it.copy(isDialogVisible = true, isLoading = false) }
    }

    override fun onSearchCleared() {
        updateState {
            it.copy(
                searchQuery = "",
                isLoading = false,
                isDialogVisible = false,
                filterItemUiState = FilterItemUiState(),
            )
        }
    }

    override fun onCancelButtonClicked() {
        updateState {
            it.copy(
                isDialogVisible = false,
                filterItemUiState = it.filterItemUiState.copy(isLoading = false)
            )
        }
    }

    override fun onRatingStarChanged(ratingIndex: Float) {
        updateState { it.copy(filterItemUiState = it.filterItemUiState.copy(selectedRating = ratingIndex)) }
    }

    override fun onGenreButtonChanged(genreType: GenreType) {
        updateState {
            it.copy(
                filterItemUiState = it.filterItemUiState.copy(
                    selectedGenre = genreType,
                    mediaGenres = it.filterItemUiState.mediaGenres.selectByGenre(genreType)
                )
            )
        }
    }

    override fun onApplyButtonClicked() {
        updateState { it.copy(filterTrigger = !it.filterTrigger, isLoading = true) }
    }

    override fun onClearButtonClicked() {
        updateState { it.copy(filterItemUiState = FilterItemUiState()) }
    }

    fun loadRecentSearch() {
        viewModelScope.launch {
            val recentHistoryQueries = getRecentHistoryUseCase()
            _recentSearchState.value = recentHistoryQueries
        }
    }

    fun deleteQueryFromHistory(query: String) {
        viewModelScope.launch {
            deleteQueryFromHistoryUseCase(query)
            loadRecentSearch()
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            clearSearchHistoryUseCase()
            loadRecentSearch()
        }
    }

    fun onItemClicked(query: String) {
        updateState { it.copy(searchQuery = query, isLoading = true) }
    }

}

