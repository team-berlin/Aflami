package com.berlin.aflami.viewmodel.profile.watchhistory

import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface WatchHistoryInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(mediaId: Long, mediaType: MediaType)
    fun onTabOptionClicked(tabOption: TabOption)

}
