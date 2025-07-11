package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.uistate.TVShowUiState

data class SearchTvShowUiState(
    val isLoading: Boolean = false,
    val tvShowName: String = "",
    val tvShow: List<TVShowUiState> = emptyList(),
    val error: String? = null,
    val searchCompleted: Boolean = false
)