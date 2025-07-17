package com.berlin.aflami.viewmodel.searchcountry

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.util.getCountriesNames
import com.berlin.aflami.viewmodel.util.getCountryIsoCode
import com.berlin.entity.Movie
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

    override fun onCountryNameChanged(countryName: CharSequence) {
        _state.update {
            it.copy(
                query = countryName.toString(),
                filteredCountries = filterCountriesByName(countryName.toString()),
                dropDownExpanded = countryName.isNotBlank() && state.value.filteredCountries.isNotEmpty()
            )
        }
    }

    private fun filterCountriesByName(countryName: String): List<String> {
        return countriesNames.filter { country ->
            country.startsWith(countryName.trim(), ignoreCase = true)
        }
    }

    override fun onCountryClicked() {
        _state.update {
            it.copy(
                isLoading = true,
                isCountrySelected = true,
                dropDownExpanded = false,
                error = null
            )
        }

        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        initialLoadSize = 20,
                        enablePlaceholders = false,
                        maxSize = 40
                    ),
                    pagingSourceFactory = ::moviesPagingSourceFactory,
                ).flow
                    .map { it.map { it.toUIState() } }
                    .cachedIn(viewModelScope)
            },
            onSuccess = ::onSearchSuccess,
            onError = ::onSearchError
        )
    }

    private fun moviesPagingSourceFactory(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            getCountryIsoCode(state.value.query)?.let {
                searchByCountry(query = it, page = page)
            } ?: emptyList()
        }
    }

    private fun onSearchSuccess(movies: Flow<PagingData<MovieUIState>>) {
        _state.update { it.copy(isLoading = false, movies = movies) }
    }

    private fun onSearchError(throwable: Throwable) {
        // TODO: Handle error
        _state.update { it.copy(error = throwable.message, isLoading = false) }
    }

    override fun onDismissDropDown() {
        _state.update { it.copy(dropDownExpanded = false) }
    }

    override fun onMovieClicked(movieId: Int) {
        sendNewEffect(SearchByCountryEffect.NavigatedToMovieDetailsScreen(movieId))
    }

    override fun onBackClicked() {
        sendNewEffect(SearchByCountryEffect.NavigatedBack)
    }
}