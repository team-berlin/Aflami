package com.berlin.aflami.viewmodel.home


import com.berlin.aflami.viewmodel.uistate.MediaUiState

data class PopularMediaUiState (
    val isLoading: Boolean = false,
    val popularMedia: List<MediaUiState> = emptyList(),
    val error: String? = null
)