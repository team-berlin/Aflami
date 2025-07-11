package com.berlin.aflami.viewmodel.searchworldtour

import com.berlin.aflami.viewmodel.uistate.MovieUIState

data class WorldTourUiState(
    val isLoading: Boolean = false,
    val countryName: String = "",
    val filteredCountries: Map<String, String> = emptyMap(),
    val movies: List<MovieUIState> = emptyList(),
    val dropDownExpanded: Boolean = false,
    val error: String? = null,
)
