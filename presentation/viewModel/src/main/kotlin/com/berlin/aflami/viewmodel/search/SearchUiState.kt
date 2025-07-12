package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.uistate.MediaUiState

sealed class SearchUiState() {
    object Init : SearchUiState()

    sealed class Searching : SearchUiState() {
        object Init : Searching()
        object Loading : Searching()
        data class Success(val data: List<MediaUiState>) : Searching()
        data class Error(val errorMessage: String) : Searching()
    }

    object NoResult : SearchUiState()
}