package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.search.GenreType

interface HomeInteractionListener {
    fun onClickUpcomingMovieCard(id: Long)
    fun onChangeUpcomingMovieGenre(genre: GenreType)
    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onShowAllTopRating()
    fun onMoodPickerClicked()
}