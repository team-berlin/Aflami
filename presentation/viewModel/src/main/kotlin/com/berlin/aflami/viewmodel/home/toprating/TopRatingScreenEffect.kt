package com.berlin.aflami.viewmodel.home.toprating

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed interface TopRatingScreenEffect {
    data object NavigateBack : TopRatingScreenEffect
    data class NavigateToMediaDetailsScreen(val id: Long, val type: MediaType) : TopRatingScreenEffect
}