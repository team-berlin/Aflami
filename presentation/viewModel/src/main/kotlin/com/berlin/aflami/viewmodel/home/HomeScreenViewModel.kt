package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.base.BasePagingSource.Companion.PAGE_SIZE
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.aflami.viewmodel.mapper.toMediaUiState
import com.berlin.aflami.viewmodel.mapper.toMovieUIState
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.entity.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import usecase.GetMoviesByMoodUseCase
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetPopularMoviesUseCase
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.movie.GetUpComingMoviesUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase
import usecase.tvshow.GetPopularTVShowsUseCase
import usecase.tvshow.GetTopRatedTVShowUseCase
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMovieGenresUseCase,
    private val getWatchedMovieUseCase: ContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: ContinueWatchingTVShowUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedTVShowUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getMoviesByMoodUseCase: GetMoviesByMoodUseCase,
) : BaseViewModel<HomeScreenState, HomeScreenEffect>(HomeScreenState()),
    HomeScreenInteractionListener {

    init {
        getPopularMedia()
        getContinueWatchingMedia()
        getTopRatingMovieAndTvShows()
        loadGenresMovies()
        getUpComingMoviesByGenre()
    }

    //region popularSection
    private fun getPopularMedia() {
        tryToCall(
            call = {
                coroutineScope {
                    val movie = async { popularMoviesUseCase() }
                    val tvShow = async { popularTVShowsUseCase() }
                    val movieList = movie.await().map { it.toMediaUiState() }
                    val tvShowList = tvShow.await().map { it.toMediaUiState() }
                    interleaveMoviesAndTvShowsEqually(movieList, tvShowList)
                }
            },
            onSuccess = ::updateScreenWithNewPopularMedia,
            onError = ::updatePopularUiStateWithError
        )
    }

    private fun interleaveMoviesAndTvShowsEqually(
        movies: List<MediaUiState>,
        tvShows: List<MediaUiState>,
    ): List<MediaUiState> {
        val arrangedList = mutableListOf<MediaUiState>()

        repeat(PAGE_SIZE) { counter ->
            with(arrangedList) {
                add(movies[counter])
                add(tvShows[counter])
            }
        }
        return arrangedList
    }

    private fun updateScreenWithNewPopularMedia(newPopularMedia: List<MediaUiState>) {
        updateState { screenState ->
            screenState.copy(
                screenState.popularMediaUiState.copy(
                    popularMedia = newPopularMedia,
                    isLoading = false,
                    errorMessage = null,
                )
            )
        }
    }

    private fun updatePopularUiStateWithError(errorUiState: ErrorUiState) {
        updateState {
            it.copy(
                popularMediaUiState = it.popularMediaUiState.copy(
                    errorMessage = errorUiState.message, isLoading = false
                )
            )
        }
    }

    //endregion

    //region continueWatchingSection
    private fun getContinueWatchingMedia() {
        tryToCall(
            call = {
                coroutineScope {
                    val moviesList =
                        async { getWatchedMovieUseCase(1).map { it.toMediaUiState() } }
                    val tvShowsList =
                        async { getWatchedTVShowUseCase(1).map { it.toMediaUiState() } }

                    val movies = moviesList.await()
                    val tvShows = tvShowsList.await()
                    (movies + tvShows).shuffled()
                }
            },
            onSuccess = ::updateScreenWithNewContinueWatchingMedia,
            onError = ::updateContinueWatchingUiStateWithError
        )
    }

    private fun updateScreenWithNewContinueWatchingMedia(newContinueWatchingMedia: List<MediaUiState>) {
        updateState { screenState ->
            screenState.copy(
                continueWatchingUiState = screenState.continueWatchingUiState.copy(
                    continueWatchingMediaList = newContinueWatchingMedia,
                    isLoading = false,
                    errorMessage = null
                ),
            )
        }
    }

    private fun updateContinueWatchingUiStateWithError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                continueWatchingUiState = screenState.continueWatchingUiState.copy(errorMessage = errorUiState.message),
                isLoading = false
            )
        }
    }
    //endregion

    //region topRatingSection
    private fun getTopRatingMovieAndTvShows() {
        tryToCall(
            call = {
                coroutineScope {
                    val moviesDeferred =
                        async { getTopRatedMoviesUseCase(1).map { movie -> movie.toMediaUiState() } }
                    val seriesDeferred =
                        async { getTopRatedSeriesUseCase(1).map { tVShow -> tVShow.toMediaUiState() } }
                    val topRatedMovies = moviesDeferred.await()
                    val topRatedSeries = seriesDeferred.await()
                    (topRatedMovies + topRatedSeries).sortedByDescending { mediaUiState -> mediaUiState.rating }
                }
            },
            onSuccess = ::updateScreenWithTopRatingMedia,
            onError = ::updateTopRatingSectionWithError,
        )
    }

    private fun updateScreenWithTopRatingMedia(newTopRatedMedia: List<MediaUiState>) {
        updateState { screenState ->
            screenState.copy(
                topRatedMediaUiState = screenState.topRatedMediaUiState.copy(
                    topRatedMedia = newTopRatedMedia,
                    isLoading = false,
                    errorMessage = null,
                )
            )
        }
    }

    private fun updateTopRatingSectionWithError(errorUIState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                topRatedMediaUiState = screenState.topRatedMediaUiState.copy(
                    isLoading = false,
                    errorMessage = errorUIState.message
                )
            )
        }
    }
    //endregion

    private fun List<String>.toGenreIds(): List<Int> {
        return state.value.upcomingMoviesUiState.movieGenres.filter { this.contains(it.name) }
            .map { it.id }
    }

    //region homeScreenInteractionListener implementation
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
            HomeScreenEffect.NavigateToMediaDetailsScreen(
                state.value.moodPickerUiState.selectedMovie.id, MediaType.MOVIE
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

    override fun onSearchClicked() = sendNewEffect(HomeScreenEffect.NavigateToSearchScreen)

    override fun onShowAllContinueWatchingClicked() =
        sendNewEffect(HomeScreenEffect.NavigateToContinueWatchingScreen)


    override fun onShowAllTopRatingClicked() =
        sendNewEffect(HomeScreenEffect.NavigateToTopRatingScreen)

    override fun onMoodSelected(mood: UserMood) {
        updateState { screenState ->
            screenState.copy(
                moodPickerUiState = screenState.moodPickerUiState.copy(
                    selectedMood = UserMoodUiState(userMood = mood),
                )
            )
        }
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

    override fun onUpcomingMoviesCardClicked(id: Long) =
        sendNewEffect(HomeScreenEffect.NavigateToMediaDetailsScreen(id, MediaType.MOVIE))

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(HomeScreenEffect.NavigateToMediaDetailsScreen(mediaId, mediaType))


    override fun onChangeUpcomingMovieGenre(newGenreId: Int) {
        updateState {
            val selected = it.upcomingMoviesUiState.movieGenres.map { genre ->
                genre.copy(isSelected = genre.id == newGenreId)
            }
            it.copy(
                selectedGenres = newGenreId,
                upcomingMoviesUiState = it.upcomingMoviesUiState.copy(
                    movieGenres = selected
                ),
                isLoading = false
            )
        }
        getUpComingMoviesByGenre()
    }
    //endregion

    private fun onGetMoviesByMoodSuccess(movies: List<Movie>) {
        val moviesUiStates = movies.map { it.toMovieUIState() }
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

    private fun getUpComingMoviesByGenre() {
        tryToCall(call = {
            getUpComingMoviesUseCase()
        }, onSuccess = { movies ->
            val genreId = state.value.selectedGenres
            val filteredMovies = if (genreId == -1) {
                movies
            } else {
                movies.filter { movie ->
                    movie.genres.any { it.id == genreId }
                }
            }
            updateState { state ->
                state.copy(
                    upcomingMoviesUiState = state.upcomingMoviesUiState.copy(
                        isLoading = false,
                        upcomingMovies = filteredMovies.map { it.toMovieUIState() }),
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
                        upcomingMoviesUiState = state.upcomingMoviesUiState.copy(
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

}