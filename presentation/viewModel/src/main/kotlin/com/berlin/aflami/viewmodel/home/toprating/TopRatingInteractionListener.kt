package com.berlin.aflami.viewmodel.home.toprating

import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface TopRatingInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(mediaId: Long, mediaType: MediaType)
}