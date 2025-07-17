package com.berlin.aflami.viewmodel.uistate


sealed class SimilarMediaUiState {
    object Init : SimilarMediaUiState()
    object Loading : SimilarMediaUiState()
    data class Success(val data: List<MediaUiState>) : SimilarMediaUiState()
    data class Error(val errorMessage: String) : SimilarMediaUiState()
}