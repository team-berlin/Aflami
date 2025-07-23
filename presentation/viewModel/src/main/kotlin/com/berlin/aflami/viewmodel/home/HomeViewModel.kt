package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState

class HomeViewModel(

):BaseViewModel<HomeUiState, HomeScreenEffect>(
    HomeUiState()
),HomeInteractionListener{
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

    override fun onUpcomingClicked(genreId: Int) {
        TODO("Not yet implemented")
    }

    override fun onUpComingMovieCardClick() {
        TODO("Not yet implemented")
    }

}