package com.berlin.aflami.viewmodel.profile

sealed class ProfileScreenEffect {
    object NavigateToWatchHistoryScreen : ProfileScreenEffect()
    object NavigateToMyRatingScreen : ProfileScreenEffect()
}