package com.berlin.aflami.viewmodel.home


interface HomeInteractionListener {
    fun onClickUpcomingMovieCard(id: Long)
    fun onChangeUpcomingMovieGenre(genreId: Int)
    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onShowAllTopRating()
    fun onMoodPickerClicked()
}

