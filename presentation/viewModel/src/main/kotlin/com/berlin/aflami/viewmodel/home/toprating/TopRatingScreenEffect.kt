package com.berlin.aflami.viewmodel.home.toprating

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed interface TopRatingScreenEffect {
    data object NavigateBack : TopRatingScreenEffect
    data class NavigateToMediaDetailsScreen(val mediaId: Long, val mediaType: MediaType) : TopRatingScreenEffect
}