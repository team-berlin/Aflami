package com.berlin.aflami.viewmodel.watchedmedia

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

data class ContinueWatchingMediaUiState(
    val continueWatchingItems: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)