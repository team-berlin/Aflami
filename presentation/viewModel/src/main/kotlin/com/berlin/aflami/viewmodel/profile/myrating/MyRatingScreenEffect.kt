package com.berlin.aflami.viewmodel.profile.myrating

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class MyRatingScreenEffect {
    object NavigateBack : MyRatingScreenEffect()
    data class NavigateToDetailsScreen(val mediaId: Long, val mediaType: MediaType) : MyRatingScreenEffect()
}