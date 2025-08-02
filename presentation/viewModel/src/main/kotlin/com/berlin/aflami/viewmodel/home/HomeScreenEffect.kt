package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class HomeScreenEffect {
    object NavigateToSearchScreen : HomeScreenEffect()
    object NavigateToContinueWatchingScreen : HomeScreenEffect()
    object NavigateToTopRatingScreen : HomeScreenEffect()
    object NavigateToMoodPickerDialog : HomeScreenEffect()
    data class NavigateToMediaDetailsScreen(val mediaId: Long, val mediaType: MediaType) :
        HomeScreenEffect()
}