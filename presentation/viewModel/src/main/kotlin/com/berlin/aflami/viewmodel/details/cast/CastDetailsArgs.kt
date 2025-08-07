package com.berlin.aflami.viewmodel.details.cast

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.util.MEDIA_ID
import com.berlin.aflami.viewmodel.util.MEDIA_TYPE
import javax.inject.Inject

class CastDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle
) {
    val mediaId = savedStateHandle.get<Long>(MEDIA_ID)
    val mediaType = savedStateHandle.get<MediaType>(MEDIA_TYPE)
}