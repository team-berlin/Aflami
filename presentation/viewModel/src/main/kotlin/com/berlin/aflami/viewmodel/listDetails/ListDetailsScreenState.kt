package com.berlin.aflami.viewmodel.listDetails

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

data class ListDetailsScreenState(
    val mediaUiState:List<MediaUiState>,
    val isScreenLoading: Boolean = false,
    val errorMessage:String? = null
)
