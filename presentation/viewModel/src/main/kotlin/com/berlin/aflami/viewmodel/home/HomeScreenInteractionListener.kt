package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.mapper.UserMood

interface HomeScreenInteractionListener : DialogInteractionListener {
    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onShowAllTopRatingClicked()
    fun onMoodSelected(userMood: UserMood)
    fun onGetNowClicked(userMood: UserMood)
    fun onUpcomingMoviesCardClicked(id: Long)
    fun onMovieCardClicked(mediaId: Long)
    fun onTVShowCardClicked(mediaId: Long)
    fun onChangeUpcomingMovieGenre(newGenreId: Int)
}

interface DialogInteractionListener {
    fun onDismissMoodPickerDialog()
    fun onClickViewDetails()
    fun onClickGetAnotherMovie()
}
