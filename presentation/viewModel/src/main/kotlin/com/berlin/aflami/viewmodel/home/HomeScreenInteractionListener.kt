package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface HomeScreenInteractionListener : DialogInteractionListener {
    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onShowAllTopRatingClicked()
    fun onMoodSelected(mood: UserMood)
    fun onGetNowClicked(mood: UserMood)
    fun onUpcomingMoviesCardClicked(id: Long)
    fun onMediaCardClicked(mediaId: Long, mediaType: MediaType)
    fun onChangeUpcomingMovieGenre(newGenreId: Int)
}

interface DialogInteractionListener {
    fun onDismissMoodPickerDialog()
    fun onClickViewDetails()
    fun onClickGetAnotherMovie()
}
