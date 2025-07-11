package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.uistate.MovieUIState

data class SearchMoviesUiState(
    val isLoading: Boolean = false,
    val movieName: String = "",
    val movies: List<MovieUIState> = emptyList(),
    val error: String? = null,
    val searchCompleted: Boolean = false
)