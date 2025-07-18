package com.berlin.aflami.viewmodel.uistate

sealed class ReviewState {
    sealed class Reviewing : ReviewState() {
        object Loading : Reviewing()
        data class Success(val data: List<ReviewUiState>) : Reviewing()
        data class Error(val errorMessage: String) : Reviewing()
    }
    object NoReviewFound : ReviewState()
}