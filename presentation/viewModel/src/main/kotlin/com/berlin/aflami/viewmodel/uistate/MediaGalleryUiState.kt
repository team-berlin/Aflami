package com.berlin.aflami.viewmodel.uistate

sealed class MediaGalleryUiState {
    object Init : MediaGalleryUiState()
    object Loading : MediaGalleryUiState()
    data class Success(val data: List<String>) : MediaGalleryUiState()
    data class Error(val errorMessage: String) : MediaGalleryUiState()
}