package com.berlin.aflami.viewmodel.home

sealed class HomeScreenEffect {
    object NavigateToSearchScreen : HomeScreenEffect()
    object NavigateToContinueWatchingScreen : HomeScreenEffect()
    object NavigateToTopRatingScreen : HomeScreenEffect()
    object NavigateToMoodPickerDialog : HomeScreenEffect()
    data class NavigateToMovieDetailsScreen(val movieId: Long) :
        HomeScreenEffect()

    data class NavigateToTVShowDetailsScreen(val tvShowId: Long) :
        HomeScreenEffect()
}