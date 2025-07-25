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
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
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

    private var movieFilterState = FilterTabSelected()
    private var tvShowFilterState = FilterTabSelected()
    private var movieGenres = FilterItemUiState.defaultGenres
    private var tvShowGenres = FilterItemUiState.defaultGenres

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
                            val selectedRating = state.value.filterItemUiState.filterTabSelected.selectedRating
                            val selectedGenreId = state.value.filterItemUiState.filterTabSelected.selectedGenres
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
                            val selectedRating = state.value.filterItemUiState.filterTabSelected.selectedRating
                            val selectedGenreId = state.value.filterItemUiState.filterTabSelected.selectedGenres
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
        when (state.value.selectedTabOption) {
            TabOption.MOVIES -> {
                movieFilterState = state.value.filterItemUiState.filterTabSelected
                movieGenres = state.value.filterItemUiState.genreUiStates
            }
            TabOption.TV_SHOWS -> {
                tvShowFilterState = state.value.filterItemUiState.filterTabSelected
                tvShowGenres = state.value.filterItemUiState.genreUiStates
            }
        }
        val (filterState, genres) = when (tabOption) {
            TabOption.MOVIES -> Pair(movieFilterState, movieGenres)
            TabOption.TV_SHOWS -> Pair(tvShowFilterState, tvShowGenres)
        }
        updateState {
            it.copy(
                isLoading = true,
                selectedTabOption = tabOption,
                filterItemUiState = FilterItemUiState(
                    filterTabSelected = filterState.copy(genreType = tabOption),
                    genreUiStates = genres,
                    isLoading = false
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
                filterItemUiState = FilterItemUiState(
                    filterTabSelected = when (state.value.selectedTabOption) {
                        TabOption.MOVIES -> movieFilterState.copy(selectedRating = 1f, selectedGenres = -1, genreType = TabOption.MOVIES)
                        TabOption.TV_SHOWS -> tvShowFilterState.copy(selectedRating = 1f, selectedGenres = -1, genreType = TabOption.TV_SHOWS)
                    },
                    genreUiStates = FilterItemUiState.defaultGenres,
                    isLoading = false
                ),
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

    override fun onRatingStarChanged(ratingIndex: Float) {
        updateState {
            val newFilterTabSelected = it.filterItemUiState.filterTabSelected.copy(
                selectedRating = ratingIndex,
                genreType = state.value.selectedTabOption
            )
            when (state.value.selectedTabOption) {
                TabOption.MOVIES -> movieFilterState = newFilterTabSelected
                TabOption.TV_SHOWS -> tvShowFilterState = newFilterTabSelected
            }
            it.copy(
                filterItemUiState = it.filterItemUiState.copy(
                    filterTabSelected = newFilterTabSelected
                )
            )
        }
    }

    override fun onFilterGenreChanged(genreId: Int) {
        val currentTab = state.value.selectedTabOption
        val currentGenres = state.value.filterItemUiState.genreUiStates
        val isValidGenre = currentGenres.any { it.id == genreId }
        if (isValidGenre) {
            val newFilterTabSelected = state.value.filterItemUiState.filterTabSelected.copy(
                selectedGenres = genreId,
                genreType = currentTab
            )
            when (currentTab) {
                TabOption.MOVIES -> {
                    movieFilterState = newFilterTabSelected
                    movieGenres = state.value.filterItemUiState.genreUiStates.map { genre ->
                        genre.copy(isSelected = genre.id == genreId)
                    }
                }
                TabOption.TV_SHOWS -> {
                    tvShowFilterState = newFilterTabSelected
                    tvShowGenres = state.value.filterItemUiState.genreUiStates.map { genre ->
                        genre.copy(isSelected = genre.id == genreId)
                    }
                }
            }
            updateState {
                it.copy(
                    filterItemUiState = it.filterItemUiState.copy(
                        filterTabSelected = newFilterTabSelected,
                        genreUiStates = state.value.filterItemUiState.genreUiStates.map { genre ->
                            genre.copy(isSelected = genre.id == genreId)
                        },
                        isLoading = true
                    ),
                    filterTrigger = !it.filterTrigger
                )
            }
        } else {
            Log.w("SearchViewModel", "Invalid genre ID $genreId for $currentTab")
        }
    }

    override fun onApplyButtonClicked() {
        updateState { it.copy(filterTrigger = !it.filterTrigger, isDialogVisible = false, isLoading = true) }
    }

    override fun onClearButtonClicked() {
        val currentTab = state.value.selectedTabOption
        val newFilterTabSelected = FilterTabSelected(
            selectedRating = 1f,
            selectedGenres = -1,
            genreType = currentTab
        )
        when (currentTab) {
            TabOption.MOVIES -> {
                movieFilterState = newFilterTabSelected
                movieGenres = FilterItemUiState.defaultGenres
            }
            TabOption.TV_SHOWS -> {
                tvShowFilterState = newFilterTabSelected
                tvShowGenres = FilterItemUiState.defaultGenres
            }
        }
        updateState {
            it.copy(
                filterItemUiState = FilterItemUiState(
                    filterTabSelected = newFilterTabSelected,
                    genreUiStates = FilterItemUiState.defaultGenres,
                    isLoading = false
                ),
                filterTrigger = !it.filterTrigger
            )
        }
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
            val currentFilterState = state.value.filterItemUiState.filterTabSelected
            tryToCall(
                call = {
                    val genres = when (selectedTab) {
                        TabOption.MOVIES -> getMovieGenresUseCase(language)
                        TabOption.TV_SHOWS -> getSeriesGenresUseCase(language)
                    }

                    val selectedGenreId = currentFilterState.selectedGenres
                    val all = GenreUiState(-1, "All", isSelected = selectedGenreId == -1)
                    val real = genres.map { genre ->
                        GenreUiState(
                            id = genre.id ?: -2,
                            name = genre.name ?: "Unknown",
                            isSelected = genre.id == selectedGenreId
                        )
                    }
                    listOf(all) + real
                },
                onSuccess = { filterGenres ->
                    when (selectedTab) {
                        TabOption.MOVIES -> movieGenres = filterGenres
                        TabOption.TV_SHOWS -> tvShowGenres = filterGenres
                    }
                    updateState {
                        it.copy(
                            filterItemUiState = it.filterItemUiState.copy(
                                filterTabSelected = it.filterItemUiState.filterTabSelected.copy(
                                    genreType = selectedTab
                                ),
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

    fun onItemClicked(query: String) {
        updateState { it.copy(searchQuery = query, isLoading = true) }
    }
}