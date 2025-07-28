package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class HomeScreenEffect {

    object NavigateToSearch:HomeScreenEffect()
    object NavigateToContinueWatching:HomeScreenEffect()
    object NavigateToTopRating:HomeScreenEffect()
    object NavigateToMoodPickerDialog:HomeScreenEffect()
    data class NavigateToMovieDetails(val id:Long, val mediaType: String):HomeScreenEffect()
}