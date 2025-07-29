package com.berlin.aflami.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.awaitAll
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

    init {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val popularDeferred = async { popularMedia("en-US") }
            val continueWatchingDeferred = async { getContinueWatchingMedia() }
            val topRatedDeferred = async { getTopRatingMovieAndTvShows() }
            val upcomingDeferred = async { getUpComingMoviesByGenre() }
            val genresDeferred = async { loadGenresMovies() }

            // Wait for all to complete
            awaitAll(
                popularDeferred,
                continueWatchingDeferred,
                topRatedDeferred,
                upcomingDeferred,
                genresDeferred
            )

            updateState { it.copy(isLoading = false) }
        }
    }


    private fun popularMedia(language: String) {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val movie = async { popularMoviesUseCase(language) }
                    val tvShow = async { popularTVShowsUseCase(language) }

                    val movieList = movie.await().map { it.toUIState() }
                    val tvShowList = tvShow.await().map { it.toUIState() }

                    _movies.value = movieList
                    _tvShows.value = tvShowList

                    combineMediaAndUpdateUi()
                }
            } catch (e: Error) {
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
                Log.d("CombinedMediaList", "$combinedList")
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
        return state.value.movieGenres.filter { this.contains(it.name) }.map { it.id }
    }

    override fun onGetNowClicked(selectedMood: UserMood) {
        updateState { it.copy(moodPickerUiState = it.moodPickerUiState.copy(openMovieDialog = true)) }
        tryToCall(
            call = {
                getMoviesByMoodUseCase(selectedMood.moodGenres.toGenreIds()).also {
                    Log.d(
                        "HomeViewModel",
                        "Selected Mood: $selectedMood, Genres: ${selectedMood.moodGenres} movies: $it"
                    )
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
            HomeScreenEffect.NavigateToMovieDetails(
                state.value.moodPickerUiState.movies.first().id, MediaType.MOVIE.name
            )
        )
    }

    override fun onClickGetAnotherMovie() {
        val currentMovieIndex = state.value.moodPickerUiState.movies.indexOf(
            state.value.moodPickerUiState.selectedMovie
        )
        val nextMovie: MovieUIState
        if (currentMovieIndex == state.value.moodPickerUiState.movies.size - 1) {
            nextMovie = state.value.moodPickerUiState.movies[0]
            return
        }
        nextMovie = state.value.moodPickerUiState.movies[currentMovieIndex + 1]
        updateState { it.copy(moodPickerUiState = it.moodPickerUiState.copy(selectedMovie = nextMovie)) }
    }


    override fun onClickUpcomingMovieCard(id: Long) {
        sendNewEffect(HomeScreenEffect.NavigateToMovieDetails(id, MediaType.MOVIE.name))
    }

    override fun onClickPopularMovieCard(id: Long, mediaType: MediaType) {
        sendNewEffect(HomeScreenEffect.NavigateToMovieDetails(id, mediaType.name))
    }


    override fun onChangeUpcomingMovieGenre(genreId: Int) {
        updateState {
            val selected = it.movieGenres.map { genre ->
                genre.copy(isSelected = genre.id == genreId)
            }
            it.copy(
                selectedGenres = genreId, movieGenres = selected, isLoading = true
            )
        }
        getUpComingMoviesByGenre()
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
                    movie.genre.contains(genreId) == true
                }
            }
            updateState { state ->
                state.copy(
                    upcomingMovies = filteredMovies.map { movie ->
                        movie.toUIState()
                    }
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

    private fun loadGenresMovies(language: String = "en") {
        tryToCall(
            call = {
                val movieGenres = getMoviesByGenreUseCase(language)
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
                        movieGenres = genreMovie,
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
        tryToCall(
            call = {
                coroutineScope {
                    val moviesList = async { getWatchedMovieUseCase().map { it.toUIStateMedia() } }
                    val tvShowsList =
                        async { getWatchedTVShowUseCase().map { it.toUIStateMedia() } }

                    val movies = moviesList.await()
                    val tvShows = tvShowsList.await()

                    val combinedList = (movies + tvShows).shuffled()
                    combinedList
                }
            },
            onSuccess = { continueWatchingMedia ->
                _state.update {
                    it.copy(
                        mediaContinueWatching = continueWatchingMedia,
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