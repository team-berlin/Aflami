package com.berlin.aflami.viewmodel.profile

sealed class ProfileScreenEffect {
    object NavigateToWatchHistoryScreen : ProfileScreenEffect()
    object NavigateToMyRatingScreen : ProfileScreenEffect()
    object NavigateToChangePasswordScreen : ProfileScreenEffect()
    object NavigateToLoginScreen : ProfileScreenEffect()
    object RefreshActivity : ProfileScreenEffect()
}