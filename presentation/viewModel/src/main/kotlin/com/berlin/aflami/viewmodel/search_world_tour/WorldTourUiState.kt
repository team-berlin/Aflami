package com.berlin.aflami.viewmodel.search_world_tour

import com.berlin.aflami.viewmodel.uistate.MovieUIState

data class WorldTourUiState(
    val isLoading: Boolean = false,
    val countryName: String = "",
    val movies: List<MovieUIState> = emptyList(),
    val error: String?=null
)