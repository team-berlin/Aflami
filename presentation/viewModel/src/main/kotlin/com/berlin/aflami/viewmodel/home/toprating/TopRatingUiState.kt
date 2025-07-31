package com.berlin.aflami.viewmodel.home.toprating

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

data class TopRatingUiState(
    val topRatedMedia: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)