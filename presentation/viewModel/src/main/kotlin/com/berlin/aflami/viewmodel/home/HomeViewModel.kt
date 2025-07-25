package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.search.GenreUiState
import usecase.GetMovieGenresUseCase
import usecase.GetUpComingMoviesUseCase


class HomeViewModel(
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMovieGenresUseCase,
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()), HomeInteractionListener {
    override fun onSearchClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllContinueWatchingClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllTopRating() {
        TODO("Not yet implemented")
    }

    override fun onMoodPickerClicked() {
        TODO("Not yet implemented")
    }

    init {
        loadGenresMovies()
        getUpComingMoviesByGenre()
    }


    override fun onClickUpcomingMovieCard(id: Long) {
        sendNewEffect(
            HomeUiEffect.NavigatedToMovieDetailsScreen(
                id.toInt()
            )
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