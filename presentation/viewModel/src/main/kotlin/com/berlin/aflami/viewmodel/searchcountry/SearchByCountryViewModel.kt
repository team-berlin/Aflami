package com.berlin.aflami.viewmodel.searchcountry

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.aflami.viewmodel.util.getCountriesNames
import com.berlin.aflami.viewmodel.util.getCountryIsoCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import usecase.SearchByCountryUseCase

class SearchByCountryViewModel(
    private val searchByCountry: SearchByCountryUseCase
) : BaseViewModel<SearchByCountryScreenUiState, SearchByCountryEffect>(
    SearchByCountryScreenUiState()
), SearchByCountryInteractionListener {

    private val countriesNames = getCountriesNames()

    override fun onCountryNameChanged(countryName: TextFieldValue) {
        _state.update {
            it.copy(
                query = countryName,
                filteredCountries = filterCountriesByName(countryName),
                dropDownExpanded = countryName.text.isNotBlank()
                        && state.value.filteredCountries.isNotEmpty()
            )
        }
    }

    private fun filterCountriesByName(countryName: TextFieldValue): List<String> {
        return countriesNames.filter { country ->
            country.startsWith(countryName.text.trim(), ignoreCase = true)
        }
    }

    override fun onCountryClicked() {
        _state.update { it.copy(
            isLoading = true,
            isCountrySelected = true,
            dropDownExpanded = false,
            error = null
        ) }

        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(pageSize = 10, initialLoadSize = 10),
                    pagingSourceFactory = {
                        BasePagingSource(
                            call = { page ->
                                getCountryIsoCode(state.value.query.text)?.let {
                                    searchByCountry.invoke(query = it, page = page)
                                } ?: emptyList()
                            }
                        )
                    },
                ).flow
                    .map { it.map { it.toUIState() } }
                    .cachedIn(viewModelScope)
            },
            onSuccess = ::onSearchSuccess,
            onError = ::onSearchError
        )
    }

    private fun onSearchSuccess(movies: Flow<PagingData<MovieUIState>>) {
        _state.update { it.copy(isLoading = false, movies = movies) }
    }

    private fun onSearchError(error: ErrorUiState) {
        // TODO: Handle error
        _state.update { it.copy(error = error.message, isLoading = false) }
    }

    override fun onDismissDropDown() {
        _state.update { it.copy(dropDownExpanded = false) }
    }

    override fun onMovieClicked(movieId: Int) {
        sendNewEffect(SearchByCountryEffect.NavigatedToMovieDetailsScreen(movieId,"MOVIE"))
    }

    override fun onBackClicked() {
        sendNewEffect(SearchByCountryEffect.NavigatedBack)
    }
}