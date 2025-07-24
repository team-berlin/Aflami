package com.berlin.aflami.viewmodel.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.uistate.TVShowUiState
import com.berlin.aflami.viewmodel.util.MediaType
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
import usecase.GetMovieGenresUseCase
import usecase.GetRecentHistoryUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.GetSeriesGenresUseCase
import usecase.SaveRecentHistoryUseCase

class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTvShowsUseCase,
    private val getRecentHistoryUseCase: GetRecentHistoryUseCase,
    private val saveRecentHistoryUseCase: SaveRecentHistoryUseCase,
    private val deleteQueryFromHistoryUseCase: DeleteQueryFromHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase
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
            call = { getRecentHistoryUseCase() },
            onSuccess = ::onLoadRecentSearchesSuccess,
            onError = {
                updateState {
                    it.copy(
                        errorMessage = it.errorMessage ?: "Failed to load recent searches",
                        isLoading = false
                    )
                }
            }
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
                _state.map { it.filterTrigger }.distinctUntilChanged()
            ) { query, _ -> query }
                .collectLatest {
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
                    config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                    pagingSourceFactory = {
                        BasePagingSource(
                            call = { page -> searchTvShowsUseCase.invoke(query = query, page = page) }
                        )
                    }
                ).flow
                    .map { it.map { it.toUiState() } }
                    .map { pagingData ->
                        pagingData.filter { tvUiState ->
                            val selectedRating = state.value.filterItemUiState.filterMovieSelected.selectedRating
                            val selectedGenreId = state.value.filterItemUiState.filterMovieSelected.selectedGenres
                            val matchesRating = tvUiState.rating.toFloatOrNull()?.let { it > selectedRating } == true
                            val matchesGenre = selectedGenreId == -1 || tvUiState.genre.any { it == selectedGenreId }
                            matchesGenre && matchesRating
                        }
                    }.cachedIn(viewModelScope)
            },
            onSuccess = ::onFetchTvShowsSuccess,
            onError = { error ->
                updateState {
                    it.copy(errorMessage = error.message, isLoading = false)
                }
            }
        )
    }

    private fun fetchMoviesByQuery(query: String) {
        updateState { it.copy(isLoading = true) }
        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(pageSize = 10, initialLoadSize = 10),
                    pagingSourceFactory = {
                        BasePagingSource(
                            call = { page -> searchMoviesUseCase.invoke(query = query, page = page) }
                        )
                    }
                ).flow
                    .map { pagingData -> pagingData.map { it.toUIState() } }
                    .map { pagingData ->
                        pagingData.filter { movieUiState ->
                            val selectedRating = state.value.filterItemUiState.filterMovieSelected.selectedRating
                            val selectedGenreId = state.value.filterItemUiState.filterMovieSelected.selectedGenres
                            val matchesRating = movieUiState.rating.toFloatOrNull()?.let { it > selectedRating } == true
                            val matchesGenre = selectedGenreId == -1 || movieUiState.genre.any { it == selectedGenreId }
                            matchesGenre && matchesRating
                        }
                    }.cachedIn(viewModelScope)
            },
            onSuccess = ::onFetchMoviesSuccess,
            onError = { error ->
                updateState {
                    it.copy(errorMessage = error.message, isLoading = false)
                }
            }
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
        tryToCall(
            call = { saveRecentHistoryUseCase },
            onSuccess = { updateState { it.copy(isLoading = false) } },
            onError = { error -> updateState { it.copy(errorMessage = error.message, isLoading = false) } }
        )
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
        updateState { it.copy(isLoading = true) }
    }

    override fun onTabOptionClicked(tabOption: TabOption) {
        updateState {
                it.copy(
                    isLoading = true,
                    selectedTabOption = tabOption,
                    filterItemUiState = it.filterItemUiState.copy(
                        genreUiStates = it.filterItemUiState.genreUiStates,
                        isLoading = true
                    ),
                    filterTrigger = !it.filterTrigger
                )
            }
        loadFilterOptions()
        onSearchQueryChanged(state.value.searchQuery)
    }

    override fun onCardClicked(id: Int) {
        val mediaType = when (state.value.selectedTabOption) {
            TabOption.MOVIES -> MediaType.MOVIE.name
            TabOption.TV_SHOWS -> MediaType.TV_SHOW.name
        }
        sendNewEffect(SearchUiEffect.NavigatedToMovieDetailsScreen(id = id, mediaType))
    }

    override fun onRecentSearchClicked(query: String) {
        onSearchQueryChanged(query)
        observeSearchKeywordChanges()
    }

    override fun onRecentSearchCleared(query: String) {
        updateState { it.copy(isLoading = false) }
        tryToCall(
            call = { deleteQueryFromHistoryUseCase(query) },
            onSuccess = { loadRecentSearches() },
            onError = { error ->
                updateState {
                    it.copy(errorMessage = error.message, isLoading = false, isDialogVisible = false)
                }
            }
        )
    }

    override fun onAllRecentSearchesCleared() {
        updateState { it.copy(isLoading = false) }
        tryToCall(
            call = { clearSearchHistoryUseCase },
            onSuccess = ::onClearAllRecentSearchesSuccess,
            onError = { error ->
                updateState {
                    it.copy(errorMessage = error.message, isLoading = false, isDialogVisible = false)
                }
            }
        )
    }

    override fun onFilterButtonClicked() {
        updateState { it.copy(isDialogVisible = true, isLoading = false) }
        loadFilterOptions()
    }

    override fun onSearchCleared() {
        updateState {
            it.copy(
                searchQuery = "",
                isLoading = false,
                isDialogVisible = false,
                filterTrigger = !it.filterTrigger
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
    private var movieFilterState = FilterMediaSelected()
    private var tvShowFilterState = FilterMediaSelected()
    override fun onRatingStarChanged(ratingIndex: Float) {
        updateState {
            when (state.value.selectedTabOption) {
                TabOption.MOVIES -> {
                    movieFilterState = movieFilterState.copy(selectedRating = ratingIndex)
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterMovieSelected = it.filterItemUiState.filterMovieSelected.copy(selectedRating = ratingIndex)
                        )
                    )
                }
                TabOption.TV_SHOWS -> {
                    tvShowFilterState = tvShowFilterState.copy(selectedRating = ratingIndex)
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterTvShowSelected = it.filterItemUiState.filterTvShowSelected.copy(selectedRating = ratingIndex)
                        )
                    )
                }
            }
        }
    }
    private var movieGenres = FilterItemUiState.defaultGenres
    private var tvShowGenres = FilterItemUiState.defaultGenres

    override fun onFilterGenreChanged(genreId: Int) {
        val currentGenres = state.value.filterItemUiState.genreUiStates
        val isValidGenre = currentGenres.any { it.id == genreId }
        val currentTab = state.value.selectedTabOption
        val newGenres = currentGenres.map { genre ->
            genre.copy(isSelected = genre.id == genreId)
        }
        if (isValidGenre) {
            when (state.value.selectedTabOption) {
                TabOption.MOVIES -> {
                    updateState {
                        it.copy(
                            filterItemUiState = it.filterItemUiState.copy(
                                filterMovieSelected =
                                    it.filterItemUiState.filterMovieSelected.copy(selectedGenres = genreId)
                             , genreUiStates = newGenres,
                                isLoading = true
                            )
                        )
                    }
                }

                TabOption.TV_SHOWS -> {
                    updateState {
                        it.copy(
                            filterItemUiState = it.filterItemUiState.copy(
                                filterMovieSelected =
                                    it.filterItemUiState.filterTvShowSelected.copy(selectedGenres = genreId),
                                genreUiStates = newGenres,
                                isLoading = true
                            )
                        )
                    }
                }
            }
            when (currentTab) {
                TabOption.MOVIES -> movieGenres = newGenres
                TabOption.TV_SHOWS -> tvShowGenres = newGenres
            }
        }
    }

    override fun onApplyButtonClicked() {
        updateState { it.copy(filterTrigger = !it.filterTrigger, isDialogVisible = false, isLoading = true) }
    }

    override fun onClearButtonClicked() {
//        val currentTab = state.value.selectedTabOption
//        val newFilterMadiaSelected = FilterMediaSelected(
//            selectedRating = 1f,
//            selectedGenres = -1
//        )
//        when (currentTab) {
//            TabOption.MOVIES -> {
//                movieFilterState = newFilterMadiaSelected
//                movieGenres = FilterItemUiState.defaultGenres
//            }
//            TabOption.TV_SHOWS -> {
//                tvShowFilterState = newFilterMadiaSelected
//                tvShowGenres = FilterItemUiState.defaultGenres
//            }
//        }
//        updateState {
//            it.copy(
//                filterItemUiState = FilterItemUiState(
//                    filterMovieSelected = newFilterMadiaSelected,
//                    genreUiStates = FilterItemUiState.defaultGenres,
//                    isLoading = false
//                ),
//                filterTrigger = !it.filterTrigger
//            )
//        }
    }

    private fun loadRecentSearch() {
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

    private fun loadFilterOptions(language: String = "en") {
        viewModelScope.launch {
            val selectedTab = state.value.selectedTabOption
            val cachedGenres = when (selectedTab) {
                TabOption.MOVIES -> movieGenres
                TabOption.TV_SHOWS -> tvShowGenres
            }
            if (cachedGenres != FilterItemUiState.defaultGenres) {
                updateState {
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            genreUiStates = cachedGenres,
                            isLoading = false
                        )
                    )
                }
            } else {
                tryToCall(
                    call = {
                        val genres = when (selectedTab) {
                            TabOption.MOVIES -> getMovieGenresUseCase(language)
                            TabOption.TV_SHOWS -> getSeriesGenresUseCase(language)
                        }
                        val selectedGenreId = when (selectedTab) {
                            TabOption.MOVIES -> state.value.filterItemUiState.filterMovieSelected.selectedGenres
                            TabOption.TV_SHOWS -> state.value.filterItemUiState.filterTvShowSelected.selectedGenres
                        }
                        val all = GenreUiState(-1, "All", isSelected = selectedGenreId == -1)
                        val realGenre = genres.map { genre ->
                            GenreUiState(
                                id = genre.id ?: -2,
                                name = genre.name ?: "Unknown",
                                isSelected = genre.id == selectedGenreId
                            )
                        }
                        listOf(all) + realGenre
                    },
                    onSuccess = { filterGenres ->
                        when (selectedTab) {
                            TabOption.MOVIES -> movieGenres = filterGenres
                            TabOption.TV_SHOWS -> tvShowGenres = filterGenres
                        }
                        updateState {
                            it.copy(
                                filterItemUiState = it.filterItemUiState.copy(
                                    genreUiStates = filterGenres,
                                    isLoading = false
                                )
                            )
                        }
                    },
                    onError = { error ->
                        updateState {
                            it.copy(
                                errorMessage = error.message,
                                isLoading = false
                            )
                        }
                    }
                )
            }
        }
    }
    fun onItemClicked(query: String) {
        updateState { it.copy(searchQuery = query, isLoading = true) }
    }
}