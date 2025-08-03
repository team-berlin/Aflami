package com.berlin.aflami.viewmodel.mediadetails.details

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.MEDIA_ID
import javax.inject.Inject

class MediaDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val mediaId: Long? = savedStateHandle.get<Long>(MEDIA_ID)
}