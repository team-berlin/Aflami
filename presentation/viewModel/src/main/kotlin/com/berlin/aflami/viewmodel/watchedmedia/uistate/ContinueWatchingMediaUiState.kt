package com.berlin.aflami.viewmodel.watchedmedia.uistate

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

data class ContinueWatchingMediaUiState(

    val continueWatchingItems: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

