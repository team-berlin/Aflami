package com.berlin.aflami.viewmodel.home

interface HomeInteractionListener{

    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onShowAllTopRating()
    fun onMoodPickerClicked()
    fun onClickUpcomingMovieCard(id: Long)
    fun onChangeUpcomingMovieGenre(genreId: Int)
}