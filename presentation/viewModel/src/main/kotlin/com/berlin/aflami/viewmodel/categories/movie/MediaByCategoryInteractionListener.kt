package com.berlin.aflami.viewmodel.categories.movie

import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface MediaByCategoryInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(mediaId: Long)
    fun onCategoryCardClicked(category: Long)
}
