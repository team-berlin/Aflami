package com.berlin.aflami.viewmodel.search_by_actor

import com.berlin.aflami.viewmodel.uistate.MovieUIState

data class SearchByActorUiState(
    val isLoading: Boolean = false,
    val actorName: String = "",
    val media: List<MovieUIState> = emptyList(),
    val error: String?=null
)