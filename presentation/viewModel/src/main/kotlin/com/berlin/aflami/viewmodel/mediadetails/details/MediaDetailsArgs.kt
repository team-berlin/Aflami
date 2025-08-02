package com.berlin.aflami.viewmodel.mediadetails.details

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.util.MEDIA_ID
import com.berlin.aflami.viewmodel.util.MEDIA_TYPE
import javax.inject.Inject

class MediaDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val mediaId = savedStateHandle.get<Long>(MEDIA_ID)
    val mediaType = savedStateHandle.get<MediaType>(MEDIA_TYPE)
}