package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.search.GenreType
import com.berlin.aflami.viewmodel.search.selectByMovieGenre
import com.berlin.aflami.viewmodel.search.toGenreMovieType
import com.berlin.entity.Movie
import com.berlin.entity.MovieGenre
import usecase.GetUpComingMoviesUseCase

class HomeViewModel(
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val homeUiStateMapper: HomeUiStateMapper,
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
        getUpComingMoviesByGenre()
    }

    override fun onChangeUpcomingMovieGenre(genre: GenreType) {
        updateState {
            it.copy(
                upcomingMovieGenres = it.upcomingMovieGenres.selectByMovieGenre(genre)
            )
        }
        getUpComingMoviesByGenre(selectedGenre = genre.toGenreMovieType())
    }


    override fun onClickUpcomingMovieCard(id: Long) {
        sendNewEffect(
            HomeUiEffect.NavigatedToMovieDetailsScreen(
                id.toInt()
            )
        )
    }

    private fun getUpComingMoviesByGenre(selectedGenre: MovieGenre = MovieGenre.ALL) {
        updateState {
            it.copy(
                isLoading = true, error = null
            )
        }
        tryToCall(
            call = { getUpComingMoviesUseCase(selectedGenre) },
            onSuccess = ::onGetUpComingMoviesSuccess,
            onError = { error ->
                updateState { it.copy(error = error.message) }
            },
        )

    }

    private fun onGetUpComingMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                upcomingMovies = homeUiStateMapper.moviesToMoviesUiState(movies),
            )
        }
    }

}