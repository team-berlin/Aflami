package com.berlin.aflami.viewmodel.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetMovieGenresUseCase
import usecase.GetMoviesByMoodUseCase
import usecase.GetPopularMoviesUseCase
import usecase.GetPopularTVShowsUseCase
import usecase.GetTopRatedMoviesUseCase
import usecase.GetTopRatedSeriesUseCase
import usecase.GetUpComingMoviesUseCase
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase

class HomeViewModel(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMovieGenresUseCase,
    private val getWatchedMovieUseCase: GetContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: GetContinueWatchingTVShowUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getMoviesByMoodUseCase: GetMoviesByMoodUseCase,
) : BaseViewModel<HomeUiState, HomeScreenEffect>(HomeUiState()), HomeInteractionListener {

    private val _movies = MutableStateFlow<List<MediaUiState>>(emptyList())
    private val _tvShows = MutableStateFlow<List<MediaUiState>>(emptyList())
    var x = 0
    init {
        x++
        popularMedia()
        loadGenresMovies()
        getContinueWatchingMedia()
        getTopRatingMovieAndTvShows()
        getUpComingMoviesByGenre()
    }


    private fun popularMedia() {
        viewModelScope.launch {

            try {
                coroutineScope {
                    val movie = async { popularMoviesUseCase() }
                    val tvShow = async { popularTVShowsUseCase() }
                    val movieList = movie.await().map { it.toUIState() }
                    val tvShowList = tvShow.await().map { it.toUIState() }
                    _movies.value = movieList
                    _tvShows.value = tvShowList
                    combineMediaAndUpdateUi()

                }
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        error =
                            ErrorUiState(
                                message = e.message
                                    ?: "An error occurred while fetching popular media",
                            ), isLoading = false
                    )
                }
                onPopularMediaError(
                    ErrorUiState(
                        message = e.message ?: "An error occurred while fetching popular media",
                    )
                )
            }
        }
    }

    private fun combineMediaAndUpdateUi() {
        viewModelScope.launch {
            combine(_movies, _tvShows) { movieList, tvShowList ->

                val mergedList = mutableListOf<MediaUiState>()
                val maxSize = maxOf(movieList.size, tvShowList.size)

                for (i in 0 until maxSize) {
                    if (i < movieList.size) mergedList.add(movieList[i])
                    if (i < tvShowList.size) mergedList.add(tvShowList[i])
                }

                mergedList

            }.collect { combinedList ->
                updateState {
                    it.copy(
                        popularMedia = PopularMediaUiState(
                            popularMedia = combinedList
                        )
                    )
                }
            }
        }
    }


    private fun onPopularMediaError(throwable: ErrorUiState) {
        _state.update { it.copy(error = throwable, isLoading = false) }
    }

    override fun onSearchClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToSearch)
    }

    override fun onShowAllContinueWatchingClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToContinueWatching)
    }

    override fun onAllTopRatingClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToTopRating)
    }

    private fun getTopRatingMovieAndTvShows() {
        tryToCall(
            call = {
                coroutineScope {
                    val moviesDeferred =
                        async { getTopRatedMoviesUseCase(1).map { it.toUIStateMedia() } }
                    val seriesDeferred =
                        async { getTopRatedSeriesUseCase(1).map { it.toUIStateMedia() } }
                    val topRatedMovies = moviesDeferred.await()
                    val topRatedSeries = seriesDeferred.await()
                    (topRatedMovies + topRatedSeries).sortedByDescending { it.rating }
                }
            },
            onSuccess = { newTopRatedMedia ->
                _state.update { oldState ->
                    oldState.copy(
                        topRatedMediaUiState = oldState.topRatedMediaUiState.copy(
                            topRatedMedia = newTopRatedMedia,
                            isLoading = false,
                            errorMessage = null,
                        )
                    )
                }
            },
            onError = { errorUIState ->
                _state.update { oldState ->
                    oldState.copy(
                        topRatedMediaUiState = oldState.topRatedMediaUiState.copy(
                            topRatedMedia = emptyList(),
                            isLoading = false,
                            errorMessage = errorUIState.message
                        )
                    )
                }
            },
            dispatcher = Dispatchers.Default
        )
    }


    override fun onSelectedMood(mood: UserMood) {
        updateState {
            it.copy(
                moodPickerUiState = it.moodPickerUiState.copy(
                    selectedMood = UserMoodUiState(userMood = mood),
                )
            )
        }
    }

    private fun List<String>.toGenreIds(): List<Int> {

        return state.value.upcomingMoviesSectionUiState.movieGenres.filter { this.contains(it.name) }
            .map { it.id }
    }

    override fun onGetNowClicked(selectedMood: UserMood) {
        updateState { it.copy(moodPickerUiState = it.moodPickerUiState.copy(openMovieDialog = true)) }
        tryToCall(
            call = {
                getMoviesByMoodUseCase(selectedMood.moodGenres.toGenreIds()).also {

                }
            },
            onSuccess = ::onGetMoviesByMoodSuccess,
            onError = { ::onError },
        )
    }

    private fun onGetMoviesByMoodSuccess(movies: List<Movie>) {
        val moviesUiStates = movies.map { it.toUIState() }
        updateState {
            it.copy(
                moodPickerUiState = it.moodPickerUiState.copy(
                    movies = moviesUiStates,
                    isLoading = false,
                    selectedMovie = moviesUiStates.firstOrNull() ?: MovieUIState()
                )
            )
        }
    }

    private fun onError(e: Exception) {
        updateState {
            it.copy(
                moodPickerUiState = it.moodPickerUiState.copy(
                    isLoading = false, error = ErrorUiState(e.message ?: "An error occurred")
                )
            )
        }
    }


    override fun onDismissMoodPickerDialog() {
        updateState {
            it.copy(
                moodPickerUiState = it.moodPickerUiState.copy(
                    openMovieDialog = false, isLoading = true
                )
            )
        }
    }

    override fun onClickViewDetails() {
        onDismissMoodPickerDialog()
        sendNewEffect(
            HomeScreenEffect.NavigateToDetails(
                state.value.moodPickerUiState.selectedMovie.id, MediaType.MOVIE.name
            )
        )
    }

    override fun onClickGetAnotherMovie() {
        val currentMovieIndex = state.value.moodPickerUiState.movies.indexOf(
            state.value.moodPickerUiState.selectedMovie
        )
        val nextMovie: MovieUIState
        if (currentMovieIndex == state.value.moodPickerUiState.movies.size - 1) {
            if (!state.value.moodPickerUiState.movies.isEmpty())
                nextMovie = state.value.moodPickerUiState.movies[0]
            return
        }
        nextMovie = state.value.moodPickerUiState.movies[currentMovieIndex + 1]
        updateState { it.copy(moodPickerUiState = it.moodPickerUiState.copy(selectedMovie = nextMovie)) }
    }

    override fun onClickUpcomingMovieCard(id: Long) {
        sendNewEffect(HomeScreenEffect.NavigateToDetails(id, MediaType.MOVIE.name))
    }

    override fun onChangeUpcomingMovieGenre(genreId: Int) {
        updateState {
            val selected = it.upcomingMoviesSectionUiState.movieGenres.map { genre ->
                genre.copy(isSelected = genre.id == genreId)
            }
            it.copy(
                selectedGenres = genreId,
                upcomingMoviesSectionUiState = it.upcomingMoviesSectionUiState.copy(
                    movieGenres = selected
                ),
                isLoading = false
            )
        }
        getUpComingMoviesByGenre()
    }

    override fun onClickCard(id: Long, mediaType: MediaType) {
        sendNewEffect(HomeScreenEffect.NavigateToDetails(id, mediaType.name))
    }

    private fun getUpComingMoviesByGenre() {
        tryToCall(call = {
            getUpComingMoviesUseCase()
        }, onSuccess = { movies ->
            val genreId = state.value.selectedGenres
            val filteredMovies = if (genreId == -1) {
                movies
            } else {
                movies.filter { movie ->
                    movie.genres.contains(genreId) == true
                }
            }
            updateState { state ->
                state.copy(
                    upcomingMoviesSectionUiState = state.upcomingMoviesSectionUiState.copy(
                        isLoading = false, upcomingMovies = filteredMovies.map { it.toUIState() }),
                )
            }
        }, onError = { error ->
            updateState { state ->
                state.copy(
                    error = error, isLoading = false
                )
            }
        })
    }

    private fun loadGenresMovies() {
        tryToCall(
            call = {
                val movieGenres = getMoviesByGenreUseCase()
                val all = GenreUiState(
                    id = -1, name = "All", isSelected = true
                )
                val genres = movieGenres.map { genre ->
                    GenreUiState(
                        id = genre.id ?: -1, name = genre.name ?: "Unknown", isSelected = false
                    )
                }
                listOf(all) + genres
            },
            onSuccess = { genreMovie ->
                updateState { state ->
                    state.copy(
                        upcomingMoviesSectionUiState = state.upcomingMoviesSectionUiState.copy(
                            movieGenres = genreMovie, isLoading = false
                        )
                    )
                }
            },
            onError = { error ->
                updateState { state ->
                    state.copy(
                        error = error, isLoading = false
                    )
                }
            },
        )
    }

    fun getContinueWatchingMedia() {
        var combinedList: List<MediaUiState> = emptyList()
        _state.update {
            it.copy(isLoading = true, error = null)
        }
        tryToCall(
            call = {
                Pager(
                    pagingSourceFactory = {
                        BasePagingSource { page ->
                            coroutineScope {
                                val moviesList =
                                    async { getWatchedMovieUseCase(1).map { it.toUIStateMedia() } }
                                val tvShowsList =
                                    async { getWatchedTVShowUseCase(1).map { it.toUIStateMedia() } }

                                val movies = moviesList.await()
                                val tvShows = tvShowsList.await()

                                combinedList = (movies + tvShows).shuffled()
                                combinedList
                            }
                        }
                    },
                    config = PagingConfig(
                        pageSize = combinedList.size, initialLoadSize = combinedList.size
                    ),
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { continueWatchingMedia ->
                _state.update {
                    it.copy(
                        mediaContinueWatching = continueWatchingMedia,
                        isLoading = false
                    )
                }

            },
            onError = { throwable ->
                _state.update {
                    it.copy(
                        error = throwable
                    )
                }
            },
        )

    }
}