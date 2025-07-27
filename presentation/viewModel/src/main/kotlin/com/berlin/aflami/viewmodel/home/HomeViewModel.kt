package com.berlin.aflami.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetMovieGenresUseCase
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
) : BaseViewModel<HomeUiState, HomeScreenEffect>(HomeUiState()), HomeInteractionListener {

    private val _movies = MutableStateFlow<List<MediaUiState>>(emptyList())
    private val _tvShows = MutableStateFlow<List<MediaUiState>>(emptyList())

    init {
        loadGenresMovies()
        getUpComingMoviesByGenre()
        popularMedia("en-US")
    }

    private fun popularMedia(language: String) {

        updateState {
            it.copy(isLoading = true, error = null)
        }
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
                        isLoading = false, popularMedia = PopularMediaUiState(
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

    override fun onShowAllTopRating() {
        viewModelScope.launch {
            tryToCall(
                call = {
                    val topRatedMovies =
                        getTopRatedMoviesUseCase(1).map { movie -> movie.toUIStateMedia() }
                    val topRatedSeries =
                        getTopRatedSeriesUseCase(1).map { series -> series.toUIStateMedia() }
                    (topRatedMovies + topRatedSeries).sortedByDescending { it.rating }
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
                                topRatedMedia = null,
                                isLoading = false,
                                errorMessage = errorUIState.message
                            )
                        )
                    }
                },
                dispatcher = Dispatchers.Default
            )
        }
    }

    override fun onMoodPickerClicked() {
    }


    override fun onClickUpcomingMovieCard(id: Long) {
        sendNewEffect(HomeScreenEffect.NavigateToMovieDetails(id, MediaType.MOVIE.name))
    }

    override fun onClickPopularMovieCard(id: Long, mediaType: MediaType) {
        sendNewEffect(HomeScreenEffect.NavigateToMovieDetails(id, mediaType.name))
    }


    override fun onChangeUpcomingMovieGenre(genreId: Int) {
        updateState {
            val selected = it.upcomingMovieGenres.map { genre ->
                genre.copy(isSelected = genre.id == genreId)
            }
            it.copy(
                selectedGenres = genreId, upcomingMovieGenres = selected, isLoading = true
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
                    movie.genres.contains(genreId) == true
                }
            }
            updateState { state ->
                state.copy(
                    upcomingMovies = filteredMovies.map { movie ->
                        movie.toUIState()
                    }, isLoading = false
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
                        upcomingMovieGenres = genreMovie, isLoading = false
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
        _state.update {
            it.copy(isLoading = true, error = null)
        }
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
                        isLoading = false,
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