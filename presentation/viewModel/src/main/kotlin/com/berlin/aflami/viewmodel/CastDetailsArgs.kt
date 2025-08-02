package com.berlin.aflami.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import javax.inject.Inject

class CastDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle
) {
    val mediaId = savedStateHandle.get<Long>("mediaId")
    val mediaType = savedStateHandle.get<MediaType>("mediaType")
}