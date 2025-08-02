package com.berlin.aflami.viewmodel.home.continueWatching

import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface ContinueWatchingMediaInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(mediaId: Long, mediaType: MediaType)
}