package com.berlin.aflami.viewmodel.searchworldtour

import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WorldTourUiState(
    val isLoading: Boolean = false,
    val countryName: String = "",
    val filteredCountries: Map<String, String> = emptyMap(),
    val movies: Flow<PagingData<MovieUIState>> = emptyFlow(),
    val dropDownExpanded: Boolean = false,
    val error: String? = null,
)
