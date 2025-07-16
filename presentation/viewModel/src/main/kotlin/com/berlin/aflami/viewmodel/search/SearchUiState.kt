package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.uistate.MediaScreenState

sealed class SearchUiState() {
    object Init : SearchUiState()

    sealed class Searching : SearchUiState() {
        object Init : Searching()
        object Loading : Searching()
        data class Success(val data: List<MediaScreenState>) : Searching()
        data class Error(val errorMessage: String) : Searching()
    }

    object NoResult : SearchUiState()
}