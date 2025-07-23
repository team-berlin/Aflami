package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.uistate.MediaCastUiState
import com.berlin.entity.MediaCast

fun MediaCast.toUiState():MediaCastUiState{
    return MediaCastUiState(
        name = name,
        poster = poster
    )
}