package com.berlin.aflami.viewmodel.categories.categories

import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface CategoriesInteractionListener {
    fun onCategoryCardClicked(mediaId: Long, mediaType: MediaType)
    fun onTabOptionClicked(tabOption: TabOption)
}
