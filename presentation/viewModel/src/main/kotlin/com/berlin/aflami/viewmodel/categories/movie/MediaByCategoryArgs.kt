package com.berlin.aflami.viewmodel.categories.movie

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.CATEGORY_ID
import javax.inject.Inject

class MediaByCategoryArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val categoryId: Long? = savedStateHandle.get<Long>(CATEGORY_ID)
}