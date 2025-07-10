package com.berlin.aflami.viewmodel.search_world_tour

import com.berlin.aflami.viewmodel.uistate.MovieUIState

data class WorldTourUiState(
    val isLoading: Boolean = false,
    val countryName: String = "",
    val countriesWithCode: Map<String, String> = emptyMap(),
    val filteredCountries: Map<String, String> = emptyMap(),
    val movies: List<MovieUIState> = emptyList(),
    val dropDownExpanded: Boolean = false,
    val error: String?=null,
)
