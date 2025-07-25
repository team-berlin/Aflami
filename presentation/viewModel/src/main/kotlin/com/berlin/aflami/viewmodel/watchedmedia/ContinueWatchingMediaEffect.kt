package com.berlin.aflami.viewmodel.watchedmedia

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class ContinueWatchingMediaEffect{
    object onBackClicked:ContinueWatchingMediaEffect()
    data class NavigateToDetails(val id:Long,val type:MediaType):ContinueWatchingMediaEffect()
}