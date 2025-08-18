package com.berlin.aflami.viewmodel.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
import usecase.movie.ClearMoviesSearchHistoryUseCase
import usecase.movie.DeleteQueryFromMoviesHistoryUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetRecentMoviesHistoryUseCase
import usecase.movie.GetSearchMoviesUseCase
import usecase.movie.SaveRecentMoviesHistoryUseCase
import usecase.tvshow.ClearTVShowSearchHistoryUseCase
import usecase.tvshow.DeleteQueryFromTVShowsHistoryUseCase
import usecase.tvshow.GetRecentTVShowHistoryUseCase
import usecase.tvshow.GetSearchTVShowsUseCase
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.SaveRecentTVShowsHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTVShowsUseCase: GetSearchTVShowsUseCase,
    private val recentMoviesHistoryUseCase: GetRecentMoviesHistoryUseCase,
    private val recentTvShowHistoryUseCase: GetRecentTVShowHistoryUseCase,
    private val saveRecentMoviesHistoryUseCase: SaveRecentMoviesHistoryUseCase,
    private val saveRecentTVShowHistoryUseCase: SaveRecentTVShowsHistoryUseCase,
    private val deleteQueryFromMoviesHistoryUseCase: DeleteQueryFromMoviesHistoryUseCase,
    private val deleteQueryFromTVShowHistoryUseCase: DeleteQueryFromTVShowsHistoryUseCase,
    private val clearMoviesSearchHistoryUseCase: ClearMoviesSearchHistoryUseCase,
    private val clearTvShowSearchHistoryUseCase: ClearTVShowSearchHistoryUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase,
) : BaseViewModel<SearchUiState, SearchScreenEffect>(SearchUiState()),
    SearchScreenInteractionListener,
    FilterInteractionListener {

    private val _recentSearchState = MutableStateFlow<List<String>>(emptyList())
    val recentSearchState = _recentSearchState.asStateFlow()

    init {
        observeSearchKeywordChanges()
        loadMovieFilterGenre()
        loadTvShowFilterGenre()
        loadRecentSearch()
    }

    // region Recent Searches
    private fun loadRecentSearches() {
        updateUiStateWithLoading()
        tryToCall(
            call = {
                coroutineScope {
                    val movies = async { recentMoviesHistoryUseCase() }
                    val tvShows = async { recentTvShowHistoryUseCase() }
                    val recentSearches = movies.await() + tvShows.await()
                    recentSearches.distinct()
                }
            },
            onSuccess = ::onLoadRecentSearchesSuccess,
            onError = ::updateRecentSearchesWithError
        )
    }

    private fun updateRecentSearchesWithError(error: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorMessage = error.message ?: FAILED_RECENT_SEARCHES,
                isLoading = false
            )
        }
    }

    private fun onLoadRecentSearchesSuccess(recentSearches: List<String>) {
        updateState { it.copy(recentSearches = recentSearches, errorMessage = null) }
    }

    private fun onClearAllRecentSearchesSuccess() {
        updateState { it.copy(recentSearches = emptyList()) }
    }

    override fun onRecentSearchClicked(query: String) {
        onSearchQueryChanged(
            TextFieldValue(
                text = query,
            )
        )
    }

    override fun onRecentSearchCleared(query: String) {
        updateState { it.copy(isLoading = false) }
        tryToCall(
            call = {
                deleteQueryFromMoviesHistoryUseCase(query)
                deleteQueryFromTVShowHistoryUseCase(query)
            },
            onSuccess = { loadRecentSearches() },
            onError = ::updateRecentSearchClearedWithError,
        )
    }

    override fun onAllRecentSearchesCleared() {
        updateState { it.copy(isLoading = false) }
        tryToCall(
            call = {
                clearMoviesSearchHistoryUseCase()
                clearTvShowSearchHistoryUseCase()
            },
            onSuccess = { onClearAllRecentSearchesSuccess() },
            onError = ::updateRecentSearchClearedWithError,
        )
    }

    private fun updateRecentSearchClearedWithError(error: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorMessage = error.message,
                isLoading = false,
                isDialogVisible = false
            )
        }
    }

    // endregion

    private fun observeSearchKeywordChanges() {
        viewModelScope.launch {
            combine(
                _state.map { it.searchQuery.text.trim() }
                    .debounce(800)
                    .filter { it.isNotEmpty() }
                    .distinctUntilChanged(),
                _state.map { it.filterTrigger }.distinctUntilChanged()
            ) { query, _ ->
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
        viewModelScope.launch {
            saveRecentMoviesHistoryUseCase(query)
            saveRecentTVShowHistoryUseCase(query)
        }
    }

    //region TVShow Search
    private fun fetchTvShowsByQuery(query: String) {
        updateScreenStateToLoading()
        tryToCall(
            call = { getFilterTVShowAsFlow(query) },
            onSuccess = ::onFetchTvShowsSuccess,
            onError = ::updateScreenStateToError
        )
    }

    private fun getFilterTVShowAsFlow(query: String): Flow<PagingData<TVShowUiState>> {
        val selectedRating =
            state.value.filterItemUiState.filterTvShowSelected.selectedRating
        val selectedGenreId =
            state.value.filterItemUiState.filterTvShowSelected.selectedGenres

        return Pager(
            config = defaultPageConfigurations(),
            pagingSourceFactory = {
                TVShowSearchPagingSource(
                    searchTVShowsUseCase = searchTVShowsUseCase,
                    tvShowNameQuery = query,
                    selectedRating = selectedRating,
                    selectedGenreId = selectedGenreId
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun onFetchTvShowsSuccess(tvShowsFlow: Flow<PagingData<TVShowUiState>>) {
        updateState { it.copy(tvShows = tvShowsFlow, errorMessage = null, isLoading = false) }
    }
    // endregion

    // region Movie Search
    private fun fetchMoviesByQuery(query: String) {
        updateScreenStateToLoading()
        tryToCall(
            call = { getFilterMovieAsFlow(query) },
            onSuccess = ::onFetchMoviesSuccess,
            onError = ::updateScreenStateToError
        )
    }

    private fun getFilterMovieAsFlow(query: String): Flow<PagingData<MovieUiState>> {
        val selectedRating =
            state.value.filterItemUiState.filterMovieSelected.selectedRating
        val selectedGenreId =
            state.value.filterItemUiState.filterMovieSelected.selectedGenres

        return Pager(
            config = defaultPageConfigurations(),
            pagingSourceFactory = {
                MovieSearchPagingSource(
                    searchMoviesUseCase = searchMoviesUseCase,
                    query = query,
                    selectedRating = selectedRating,
                    selectedGenreId = selectedGenreId
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun onFetchMoviesSuccess(moviesFlow: Flow<PagingData<MovieUiState>>) {
        updateState { it.copy(movies = moviesFlow, errorMessage = null, isLoading = false) }
    }

    // endregion
    private fun updateScreenStateToError(errorUiState: ErrorUiState) =
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorUiState.message,
                isLoading = false
            )
        }

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(isLoading = true) }

    override fun onSearchActionClicked() {
        onSearchQueryChanged(state.value.searchQuery)
        tryToCall(
            call = {
                saveRecentMoviesHistoryUseCase(state.value.searchQuery.text)
                saveRecentTVShowHistoryUseCase(state.value.searchQuery.text)
            },
            onSuccess = {
                loadRecentSearch()
                updateState { it.copy(isLoading = false) }
            },
            onError = ::updateScreenStateToError
        )
    }

    override fun onSearchQueryChanged(query: TextFieldValue) {
        updateState {
            it.copy(searchQuery = query, isLoading = false)
        }
        loadRecentSearch()
    }

    override fun onBackClicked() {
        sendNewEffect(SearchScreenEffect.NavigatedBack)
    }

    override fun onWorldSearchCardClicked() {
        sendNewEffect(SearchScreenEffect.NavigateToWorldSearchScreen)
    }

    override fun onActorSearchCardClicked() {
        sendNewEffect(SearchScreenEffect.NavigateToActorSearchScreen)
    }

    private fun updateUiStateWithLoading() {
        updateState { it.copy(isLoading = true, errorMessage = null) }
    }

    override fun onTabOptionClicked(tabOption: TabOption) {
        updateState {
            it.copy(
                isLoading = false,
                selectedTabOption = tabOption,
                filterItemUiState = FilterItemUiState(
                    filterMovieSelected = state.value.filterItemUiState.filterMovieSelected,
                    filterTvShowSelected = state.value.filterItemUiState.filterTvShowSelected
                ),
                filterTrigger = !it.filterTrigger
            )
        }
        onSearchQueryChanged(state.value.searchQuery)
    }

    override fun onMediaCardClicked(mediaId: Long) {
        val mediaType = when (state.value.selectedTabOption) {
            TabOption.MOVIES -> MediaType.MOVIE.name
            TabOption.TV_SHOWS -> MediaType.TV_SHOW.name
        }
        sendNewEffect(SearchScreenEffect.NavigatedToMovieDetailsScreen(id = mediaId, mediaType))
    }

    override fun onFilterButtonClicked() {
        if (
            state.value.searchQuery.text.isEmpty().not()
        ) {
            updateState { it.copy(isDialogVisible = true, isLoading = false) }
        }

    }

    override fun onSearchCleared() {
        updateState {
            it.copy(
                searchQuery = TextFieldValue(""),
                isLoading = false,
                isDialogVisible = false,
                filterTrigger = !it.filterTrigger
            )
        }
    }

    override fun onCancelClicked() {
        updateState {
            it.copy(
                isDialogVisible = false,
                filterItemUiState = it.filterItemUiState.copy(isLoading = false)
            )
        }
    }

    override fun onRatingStarChanged(ratingIndex: Float) {
        updateState {
            when (state.value.selectedTabOption) {
                TabOption.MOVIES -> {
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterMovieSelected = it.filterItemUiState.filterMovieSelected.copy(
                                selectedRating = ratingIndex
                            )
                        )
                    )
                }

                TabOption.TV_SHOWS -> {
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterTvShowSelected = it.filterItemUiState.filterTvShowSelected.copy(
                                selectedRating = ratingIndex
                            )
                        )
                    )
                }
            }
        }
    }

    override fun onFilterGenreChanged(genreId: Int) {
        when (state.value.selectedTabOption) {
            TabOption.MOVIES -> {
                updateState {
                    val updatedGenres =
                        it.filterItemUiState.filterMovieSelected.genreUiStates.map { genre ->
                            genre.copy(isSelected = genre.id == genreId)
                        }

                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterMovieSelected = it.filterItemUiState.filterMovieSelected.copy(
                                selectedGenres = genreId,
                                genreUiStates = updatedGenres
                            )
                        ),
                        isLoading = false
                    )
                }
            }

            TabOption.TV_SHOWS -> {
                updateState {
                    val updatedGenres =
                        it.filterItemUiState.filterTvShowSelected.genreUiStates.map { genre ->
                            genre.copy(isSelected = genre.id == genreId)
                        }

                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterTvShowSelected = it.filterItemUiState.filterTvShowSelected.copy(
                                selectedGenres = genreId,
                                genreUiStates = updatedGenres
                            )
                        ),
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun onApplyButtonClicked() {
        updateState {
            it.copy(
                filterTrigger = !it.filterTrigger,
                isDialogVisible = false,
                isLoading = true
            )
        }
    }

    override fun onClearButtonClicked() {
        when (state.value.selectedTabOption) {

            TabOption.MOVIES -> {
                updateState {
                    val updatedGenres =
                        it.filterItemUiState.filterMovieSelected.genreUiStates.map { genre ->
                            genre.copy(isSelected = genre.id == -1)
                        }
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterMovieSelected = FilterMediaSelected(
                                selectedRating = 0f,
                                selectedGenres = -1,
                                genreUiStates = updatedGenres,

                                )
                        ),
                        isLoading = false
                    )
                }
            }

            TabOption.TV_SHOWS -> {
                updateState {
                    val updatedGenres =
                        it.filterItemUiState.filterTvShowSelected.genreUiStates.map { genre ->
                            genre.copy(isSelected = genre.id == -1)
                        }
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterTvShowSelected = FilterMediaSelected(
                                selectedRating = 0f,
                                selectedGenres = -1,
                                genreUiStates = updatedGenres
                            )
                        ),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadRecentSearch() {
        viewModelScope.launch {
            _recentSearchState.value = recentMoviesHistoryUseCase() + recentTvShowHistoryUseCase()
        }
    }

    fun deleteQueryFromHistory(query: String) {
        viewModelScope.launch {
            deleteQueryFromMoviesHistoryUseCase(query)
            deleteQueryFromTVShowHistoryUseCase(query)
            loadRecentSearch()
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            clearMoviesSearchHistoryUseCase()
            clearTvShowSearchHistoryUseCase()
            loadRecentSearch()
        }
    }

    private fun loadMovieFilterGenre() {
        tryToCall(
            call = {
                defaultGenreUiStates(getMovieGenresUseCase())
            },
            onSuccess = ::onFetchMovieGenresSuccess,
            onError = ::updateRecentSearchesWithError
        )
    }

    private fun onFetchMovieGenresSuccess(filterGenres: List<GenreUiState>) {
        updateState {
            it.copy(
                filterItemUiState = it.filterItemUiState.copy(
                    filterMovieSelected = it.filterItemUiState.filterMovieSelected.copy(
                        genreUiStates = filterGenres
                    )
                )
            )
        }
    }

    private fun loadTvShowFilterGenre() {
        tryToCall(
            call = {
                defaultGenreUiStates(getTVShowGenresUseCase())
            },
            onSuccess = ::onFetchTVShowGenresSuccess,
            onError = ::updateScreenStateToError
        )
    }

    private fun defaultGenreUiStates(genres: List<Genre>): List<GenreUiState> {
        val all = GenreUiState(-1, "All", isSelected = true)
        val realGenre = genres.map { genre ->
            GenreUiState(
                id = genre.id ?: -1,
                name = genre.name ?: "Unknown",
                isSelected = false
            )
        }
        return listOf(all) + realGenre
    }

    private fun onFetchTVShowGenresSuccess(filterGenres: List<GenreUiState>) {
        updateState {
            it.copy(
                filterItemUiState = it.filterItemUiState.copy(
                    filterTvShowSelected = it.filterItemUiState.filterTvShowSelected.copy(
                        genreUiStates = filterGenres
                    )
                )
            )
        }
    }

    fun onItemClicked(query: TextFieldValue) {
        updateState { it.copy(searchQuery = query, isLoading = true) }
    }

    companion object {
        const val FAILED_RECENT_SEARCHES = "Failed to load recent searches"
    }
}