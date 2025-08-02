package com.berlin.aflami.viewmodel.search

import androidx.compose.ui.text.input.TextFieldValue
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

class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTVShowsUseCase,
    private val getRecentMoviesHistoryUseCase: GetRecentMoviesHistoryUseCase,
    private val getRecentTvShowHistoryUseCase: GetRecentTVShowHistoryUseCase,
    private val saveRecentMoviesHistoryUseCase: SaveRecentMoviesHistoryUseCase,
    private val saveRecentTVShowHistoryUseCase: GetRecentTVShowHistoryUseCase,
    private val deleteQueryFromMoviesHistoryUseCase: DeleteQueryFromMoviesHistoryUseCase,
    private val deleteQueryFromTVShowHistoryUseCase: DeleteQueryFromTVShowsHistoryUseCase,
    private val clearMoviesSearchHistoryUseCase: ClearMoviesSearchHistoryUseCase,
    private val clearTvShowSearchHistoryUseCase: ClearTVShowSearchHistoryUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getSeriesGenresUseCase: GetTVShowGenresUseCase,
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteractionListener,
    FilterInteractionListener {

    private val _recentSearchState = MutableStateFlow<List<String>>(emptyList())
    val recentSearchState = _recentSearchState.asStateFlow()


    init {
        observeSearchKeywordChanges()
        loadMovieFilterGenre()
        loadTvShowFilterGenre()
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
                _state.map { it.searchQuery.text.trim() }
                    .debounce(800)
                    .filter { it.isNotEmpty() }
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
                            call = { page ->
                                searchTvShowsUseCase.invoke(
                                    query = query,
                                    page = page
                                )
                            }
                        )
                    }
                ).flow
                    .map { it.map { it.toUiState() } }
                    .map { pagingData ->
                        pagingData.filter { tvUiState ->
                            val selectedRating =
                                state.value.filterItemUiState.filterTvShowSelected.selectedRating
                            val selectedGenreId =
                                state.value.filterItemUiState.filterTvShowSelected.selectedGenres
                            val matchesRating =
                                convertArabicToEnglish(tvUiState.rating.replace('٫', '.'))
                                    .toFloatOrNull()
                                    ?.let { it > selectedRating } == true
                            val matchesGenre =
                                selectedGenreId == -1 || tvUiState.genre.any { it == selectedGenreId }
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
                            call = { page ->
                                searchMoviesUseCase.invoke(
                                    query = query,
                                    page = page
                                )
                            }
                        )
                    }
                ).flow
                    .map { pagingData -> pagingData.map { it.toUIState() } }
                    .map { pagingData ->
                        pagingData.filter { movieUiState ->
                            val selectedRating =
                                state.value.filterItemUiState.filterMovieSelected.selectedRating
                            val selectedGenreId =
                                state.value.filterItemUiState.filterMovieSelected.selectedGenres
                            val matchesRating =
                                convertArabicToEnglish(movieUiState.rating.replace('٫', '.'))
                                    .toFloatOrNull()
                                    ?.let { it > selectedRating } == true
                            val matchesGenre =
                                selectedGenreId == -1 || movieUiState.genre.any { it == selectedGenreId }
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

    private fun convertArabicToEnglish(input: String): String {
        val arabicDigits = "٠١٢٣٤٥٦٧٨٩".toCharArray()
        val englishDigits = "0123456789"

        return input.map { char ->
            val index = arabicDigits.indexOf(char)
            if (index != -1) englishDigits[index] else char
        }.joinToString("")
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
            onSuccess = {
                updateState { it.copy(isLoading = false) }
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

    override fun onSearchQueryChanged(query: TextFieldValue) {
        updateState {
            it.copy(searchQuery = query, isLoading = false)
        }
        loadRecentSearch()
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

    override fun onCardClicked(id: Long) {
        val mediaType = when (state.value.selectedTabOption) {
            TabOption.MOVIES -> MediaType.MOVIE.name
            TabOption.TV_SHOWS -> MediaType.TVSHOW.name
        }
        sendNewEffect(SearchUiEffect.NavigatedToMovieDetailsScreen(id = id, mediaType))
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
            call = { deleteQueryFromHistoryUseCase(query) },
            onSuccess = { loadRecentSearches() },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false,
                        isDialogVisible = false
                    )
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
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false,
                        isDialogVisible = false
                    )
                }
            }
        )
    }

    override fun onFilterButtonClicked() {
        updateState { it.copy(isDialogVisible = true, isLoading = false) }

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


    private fun loadMovieFilterGenre() {
        tryToCall(
            call = {
                val genres = getMovieGenresUseCase()
                val all = GenreUiState(-1, "All", isSelected = true)
                val realGenre = genres.map { genre ->
                    GenreUiState(
                        id = genre.id ?: -1,
                        name = genre.name ?: "Unknown",
                        isSelected = false
                    )
                }
                listOf(all) + realGenre
            },
            onSuccess = { filterGenres ->
                updateState {
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterMovieSelected = it.filterItemUiState.filterMovieSelected.copy(
                                genreUiStates = filterGenres
                            )
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

    private fun loadTvShowFilterGenre() {
        tryToCall(
            call = {
                val genres = getSeriesGenresUseCase()
                val all = GenreUiState(-1, "All", isSelected = true)
                val realGenre = genres.map { genre ->
                    GenreUiState(
                        id = genre.id ?: -1,
                        name = genre.name ?: "Unknown",
                        isSelected = false
                    )
                }
                listOf(all) + realGenre
            },
            onSuccess = { filterGenres ->
                updateState {
                    it.copy(
                        filterItemUiState = it.filterItemUiState.copy(
                            filterTvShowSelected = it.filterItemUiState.filterTvShowSelected.copy(
                                genreUiStates = filterGenres
                            )
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


    fun onItemClicked(query: TextFieldValue) {
        updateState { it.copy(searchQuery = query, isLoading = true) }
    }
}