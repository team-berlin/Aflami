package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface HomeInteractionListener : DialogInteractionListener {
    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onAllTopRatingClicked()
    fun onSelectedMood(mood: UserMood)
    fun onGetNowClicked(mood: UserMood)
    fun onClickUpcomingMovieCard(id: Long)
    fun onClickPopularMovieCard(id: Long, mediaType: MediaType)
    fun onChangeUpcomingMovieGenre(genreId: Int)
}

interface DialogInteractionListener {
    fun onDismissMoodPickerDialog()
    fun onClickViewDetails()
    fun onClickGetAnotherMovie()
}