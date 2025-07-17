package com.berlin.aflami.viewmodel.mediadetails


import com.berlin.aflami.viewmodel.uistate.MediaUiState

sealed class SimilarMediaUiState {
    object Loading : SimilarMediaUiState()
    data class Success(val data: List<MediaUiState>) : SimilarMediaUiState()
    data class Error(val errorMessage: String) : SimilarMediaUiState()
}