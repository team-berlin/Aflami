package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface HomeInteractionListener{

    fun onSearchClicked()
    fun onShowAllContinueWatchingClicked()
    fun onShowAllTopRating()
    fun onMoodPickerClicked(mood: UserMood)
    fun onClickUpcomingMovieCard(id: Long)
    fun onClickPopularMovieCard(id: Long, mediaType: MediaType)
    fun onChangeUpcomingMovieGenre(genreId: Int)

}