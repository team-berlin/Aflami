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
                        async { getWatchedMovieUseCase(ONE_PAGE).map { it.toMediaUiState() } }
                    val tvShowsList =
                        async { getWatchedTVShowUseCase(ONE_PAGE).map { it.toMediaUiState() } }

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
                        async { getTopRatedMoviesUseCase(ONE_PAGE).map { movie -> movie.toMediaUiState() } }
                    val seriesDeferred =
                        async { getTopRatedSeriesUseCase(ONE_PAGE).map { tVShow -> tVShow.toMediaUiState() } }
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

    override fun onMoodSelected(userMood: UserMood) {
        updateState { screenState ->
            screenState.copy(
                moodPickerUiState = screenState.moodPickerUiState.copy(
                    selectedMood = UserMoodUiState(userMood = userMood),
                )
            )
        }
    }

    //region onGetNowClicked implementation
    override fun onGetNowClicked(userMood: UserMood) {
        updateState { it.copy(moodPickerUiState = it.moodPickerUiState.copy(openMovieDialog = true)) }
        tryToCall(
            call = {
                getMoviesByMoodUseCase(userMood.moodGenres.toGenreIds())
                    .map { movie -> movie.toMovieUIState() }
            },
            onSuccess = ::updateDialogWithNewMovies,
            onError = ::updateScreenWithError,
        )
    }

    private fun updateDialogWithNewMovies(moviesUiState: List<MovieUIState>) {
        updateState { screenState ->
            screenState.copy(
                moodPickerUiState = screenState.moodPickerUiState.copy(
                    movies = moviesUiState,
                    isLoading = false,
                    selectedMovie = moviesUiState.firstOrNull() ?: MovieUIState()
                )
            )
        }
    }

    private fun updateScreenWithError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                moodPickerUiState = screenState.moodPickerUiState.copy(
                    isLoading = false,
                    error = ErrorUiState(errorUiState.message)
                )
            )
        }
    }
    //endregion

    override fun onUpcomingMoviesCardClicked(id: Long) =
        sendNewEffect(HomeScreenEffect.NavigateToMediaDetailsScreen(id, MediaType.MOVIE))

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(HomeScreenEffect.NavigateToMediaDetailsScreen(mediaId, mediaType))

    //region onChangeUpComingMovieGenre
    override fun onChangeUpcomingMovieGenre(newGenreId: Int) {
        updateState { screenState ->
            val selected = screenState.upcomingMoviesUiState.movieGenres.map { genre ->
                genre.copy(isSelected = genre.id == newGenreId)
            }
            screenState.copy(
                selectedGenres = newGenreId,
                upcomingMoviesUiState = screenState.upcomingMoviesUiState.copy(
                    movieGenres = selected
                ),
                isLoading = false
            )
        }
        getUpComingMoviesByGenre()
    }

    private fun getUpComingMoviesByGenre() {
        tryToCall(
            call = { getUpComingMoviesUseCase().map { movie -> movie.toMovieUIState() } },
            onSuccess = ::updateScreenWithNewUpComingMovies,
            onError = ::updateUpComingSectionWithError
        )
    }

    private fun updateUpComingSectionWithError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                upcomingMoviesUiState = screenState.upcomingMoviesUiState.copy(
                    errorMessage = errorUiState.message,
                    isLoading = false
                ),
            )
        }
    }

    private fun updateScreenWithNewUpComingMovies(movies: List<MovieUIState>) {
        val genreId = state.value.selectedGenres
        val filteredMovies = if (genreId == -1) {
            movies
        } else {
            movies.filter { movieUiState ->
                movieUiState.genre.any { it.id == genreId }
            }
        }
        updateState { state ->
            state.copy(
                upcomingMoviesUiState = state.upcomingMoviesUiState.copy(
                    isLoading = false,
                    upcomingMovies = filteredMovies
                ),
            )
        }
    }
    //endregion

    private fun loadGenresMovies() {
        tryToCall(
            call = {
                val movieGenres = getMoviesByGenreUseCase()
                val all = GenreUiState(
                    id = -1, name = "All", isSelected = true
                )
                val genres = movieGenres.map { genre ->
                    GenreUiState(
                        id = genre.id, name = genre.name, isSelected = false
                    )
                }
                listOf(all) + genres
            },
            onSuccess = ::updateScreenWithNewMovieGenres,
            onError = ::updateUpComingMoviesWithError
        )
    }

    private fun updateScreenWithNewMovieGenres(movieGenres: List<GenreUiState>) {
        updateState { state ->
            state.copy(
                upcomingMoviesUiState = state.upcomingMoviesUiState.copy(
                    movieGenres = movieGenres, isLoading = false
                )
            )
        }
    }

    private fun updateUpComingMoviesWithError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                upcomingMoviesUiState = screenState.upcomingMoviesUiState.copy(
                    errorMessage = errorUiState.message
                )
            )
        }
    }

    companion object {
        const val ONE_PAGE = 1
    }

}