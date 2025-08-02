package com.berlin.aflami.viewmodel.home.continueWatching

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class ContinueWatchingScreenEffect {
    object NavigateBack : ContinueWatchingScreenEffect()
    data class NavigateToDetails(val mediaId: Long, val mediaType: MediaType) : ContinueWatchingScreenEffect()
}