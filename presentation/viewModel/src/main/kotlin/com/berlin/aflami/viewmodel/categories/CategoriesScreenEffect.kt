package com.berlin.aflami.viewmodel.categories

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class CategoriesScreenEffect {
    data class NavigateToMediaScreen(val mediaId: Long, val mediaType: MediaType) : CategoriesScreenEffect()
}