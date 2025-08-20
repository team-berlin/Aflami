package com.berlin.aflami.viewmodel.profile.myrating

import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface MyRatingInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(mediaId: Long, mediaType: MediaType)
    fun onTabOptionClicked(tabOption: TabOption)
    fun retry()

}
