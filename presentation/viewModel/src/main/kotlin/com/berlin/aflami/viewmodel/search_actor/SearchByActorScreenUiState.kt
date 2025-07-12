package com.berlin.aflami.viewmodel.search_actor

import com.berlin.aflami.viewmodel.uistate.MovieUIState

data class SearchByActorScreenUiState(
    val isLoading: Boolean = false,
    val actorName: String = "",
    val movies: List<MovieUIState> = emptyList(),
    val error: String? = null
)
