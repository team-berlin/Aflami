package com.berlin.aflami.viewmodel.watchedmedia

import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface WatchedMediaInteractionListener {

    fun onBackClicked()
    fun onMediaCardClick(id: Long, type: MediaType)
}