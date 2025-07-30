package com.berlin.aflami.viewmodel.searchcountry

import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchByCountryScreenUiState(
    val isLoading: Boolean = false,
    val query: TextFieldValue = TextFieldValue(""),
    val isCountrySelected: Boolean = false,
    val filteredCountries: List<String> = emptyList(),
    val movies: Flow<PagingData<MovieUIState>> = emptyFlow(),
    val dropDownExpanded: Boolean = false,
    val error: String? = null,
)
