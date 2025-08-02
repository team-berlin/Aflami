package com.berlin.aflami.viewmodel.home

sealed class HomeScreenEffect {
    object NavigateToSearchScreen : HomeScreenEffect()
    object NavigateToContinueWatchingScreen : HomeScreenEffect()
    object NavigateToTopRatingScreen : HomeScreenEffect()
    object NavigateToMoodPickerDialog : HomeScreenEffect()
    data class NavigateToMediaDetailsScreen(val id: Long, val mediaType: String) :
        HomeScreenEffect()
}