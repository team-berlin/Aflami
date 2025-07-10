package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.uistate.TvShowUiState

data class SearchTvShowUiState(
    val isLoading: Boolean = false,
    val tvShowName: String = "",
    val tvShow: List<TvShowUiState> = emptyList(),
    val error: String? = null
)