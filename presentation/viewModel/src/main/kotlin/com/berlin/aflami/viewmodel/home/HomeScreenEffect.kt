package com.berlin.aflami.viewmodel.home

sealed class HomeScreenEffect {

    object NavigateToSearch : HomeScreenEffect()
    object NavigateToContinueWatching : HomeScreenEffect()
    object NavigateToTopRating : HomeScreenEffect()
    object NavigateToMoodPickerDialog : HomeScreenEffect()
    object NavigateToMovieDetails : HomeScreenEffect()
}