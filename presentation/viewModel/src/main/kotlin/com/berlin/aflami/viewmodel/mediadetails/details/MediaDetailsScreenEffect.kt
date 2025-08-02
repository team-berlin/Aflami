package com.berlin.aflami.viewmodel.mediadetails.details

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class MediaDetailsScreenEffect {
    object NavigateBack : MediaDetailsScreenEffect()
    data class PlayMedia(val videoUrl:String) : MediaDetailsScreenEffect()
    data class NavigateToShowAllCastScreen(
        val mediaId: Long,
        val mediaType: MediaType
    ) : MediaDetailsScreenEffect()
    data class NavigateToMediaDetails(val mediaId: Long, val mediaType: MediaType): MediaDetailsScreenEffect()
    data object NavigateToLogin : MediaDetailsScreenEffect()
}
