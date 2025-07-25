package com.berlin.aflami.viewmodel.home

interface HomeInteractionListener{

    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onShowAllTopRating()
    fun onMoodPickerClicked()
    fun onUpcomingTabClicked(genreId: Int)
    fun onUpComingMovieCardClick()

}