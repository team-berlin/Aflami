package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.search.GenreUiState
import usecase.GetMovieGenresUseCase
import usecase.GetUpComingMoviesUseCase
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.update
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase


class HomeViewModel(
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMovieGenresUseCase,
    private val getWatchedMovieUseCase: GetContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: GetContinueWatchingTVShowUseCase
):BaseViewModel<HomeUiState, HomeScreenEffect>(
    HomeUiState()
),HomeInteractionListener{
    override fun onSearchClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllContinueWatchingClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToContinueWatching)
    }

    override fun onShowAllTopRating() {
        TODO("Not yet implemented")
    }

    override fun onMoodPickerClicked() {
        TODO("Not yet implemented")
    }

    override fun onClickUpcomingMovieCard(id: Long) {
        sendNewEffect(
            HomeUiEffect.NavigatedToMovieDetailsScreen(
                id.toInt()
            )
        )
    }

    fun getContinueWatchingMedia() {
        _state.update {
            it.copy(isLoading = true, error = null)
        }
        tryToCall(
            call = {
                coroutineScope {
                    val moviesList = async {  getWatchedMovieUseCase().map { it.toUIStateMedia() }}
                    val tvShowsList = async { getWatchedTVShowUseCase().map { it.toUIStateMedia() }}

                    val movies = moviesList.await()
                    val tvShows = tvShowsList.await()

                    val combinedList = (movies + tvShows)
                        .shuffled()
                    combinedList
                }
            },
            onSuccess = {continueWatchingMedia->
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
                        error = throwable.message
                    )
                }
            },
        )

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
                    movie.genre.contains(genreId) == true
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

}