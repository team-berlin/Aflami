package com.berlin.aflami.viewmodel.categories.movie

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class MediaByCategoryScreenEffect {
    object NavigateBack : MediaByCategoryScreenEffect()
    data class NavigateToMediaDetails(val mediaId: Long, val mediaType: MediaType) : MediaByCategoryScreenEffect()
}