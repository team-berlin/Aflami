package com.berlin.aflami.viewmodel.watchedmedia

import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface ContinueWatchingMediaInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(id: Long, type: MediaType)
}