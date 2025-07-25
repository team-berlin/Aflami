package com.berlin.aflami.viewmodel.home


import com.berlin.aflami.viewmodel.uistate.MediaUiState

data class PopularMediaUiState (
    val isLoading: Boolean = false,
    val popularMedia: List<MediaUiState> = emptyList(),
    val moviesOnly: List<MediaUiState> = emptyList(),
    val tvShowsOnly: List<MediaUiState> = emptyList(),
    val error: String? = null
)