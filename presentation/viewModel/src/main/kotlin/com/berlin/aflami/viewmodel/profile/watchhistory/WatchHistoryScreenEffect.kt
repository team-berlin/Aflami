package com.berlin.aflami.viewmodel.profile.watchhistory

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class WatchHistoryScreenEffect {
    object NavigateBack : WatchHistoryScreenEffect()
    data class NavigateToDetailsScreen(val mediaId: Long, val mediaType: MediaType) : WatchHistoryScreenEffect()
}