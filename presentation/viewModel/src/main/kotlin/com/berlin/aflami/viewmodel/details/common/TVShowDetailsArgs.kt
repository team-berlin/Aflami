package com.berlin.aflami.viewmodel.details.common

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.TVSHOW_ID
import javax.inject.Inject

class TVShowDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val tvShowId: Long? = savedStateHandle.get<Long>(TVSHOW_ID)
}