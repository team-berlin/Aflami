package com.berlin.aflami.viewmodel.searchcountry

import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

//@Immutable
data class SearchByCountryScreenState(
    val isLoading: Boolean = false,
    val countryName: TextFieldValue = TextFieldValue(""),
    val isCountrySelected: Boolean = false,
    val filteredCountries: List<String> = emptyList(),
    val moviesOfCountryFlow: Flow<PagingData<MovieUiState>> = emptyFlow(),
    val dropDownExpanded: Boolean = false,
    val errorMessage: String? = null,
)
