package com.berlin.aflami.viewmodel.categories.movie

import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface MediaByCategoryInteractionListener {
    fun onMediaCardClicked(mediaId: Long, mediaType: MediaType)
    fun onCategoryCardClicked(category: Long)
}
