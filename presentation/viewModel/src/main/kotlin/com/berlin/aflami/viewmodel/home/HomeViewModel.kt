package com.berlin.aflami.viewmodel.home

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import kotlinx.coroutines.launch
import usecase.GetSearchTvShowsUseCase
import usecase.GetTopRatedMovies
import usecase.GetTopRatedSeries

class HomeViewModel(
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getTopRatedSeries: GetTopRatedSeries,
) : BaseViewModel<HomeUiState, HomeScreenEffect>(
    HomeUiState()
), HomeInteractionListener {
    override fun onSearchClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllContinueWatchingClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllTopRating() {
        viewModelScope.launch {
            val topRatedMoviesAndSeries = getTopRatedMovies(1).plus(getTopRatedSeries(1))
        }
    }

    override fun onMoodPickerClicked() {
        TODO("Not yet implemented")
    }

    override fun onUpcomingClicked(genreId: Int) {
        TODO("Not yet implemented")
    }

    override fun onUpComingMovieCardClick() {
        TODO("Not yet implemented")
    }

}