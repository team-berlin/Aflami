package com.berlin.aflami.viewmodel.watchedmedia

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class WatchedMediaEffect{
    object onBackClicked:WatchedMediaEffect()
    data class NavigateToDetails(val id:Long,val type:MediaType):WatchedMediaEffect()
}