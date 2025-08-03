package com.berlin.aflami.viewmodel.searchcountry

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.util.getCountriesNames
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import usecase.movie.SearchMoviesByCountryUseCase
import javax.inject.Inject


class SearchByCountryScreenViewModel @Inject constructor(
    private val searchMoviesByCountryUseCase: SearchMoviesByCountryUseCase,
) : BaseViewModel<SearchByCountryScreenState, SearchByCountryScreenEffect>(
    SearchByCountryScreenState()
), SearchByCountryScreenInteractionListener {

    //region SearchByCountryScreenInteractionListener implementation
    override fun onMovieClicked(movieId: Long) =
        sendNewEffect(SearchByCountryScreenEffect.NavigatedToMovieDetailsScreen(movieId, "MOVIE"))

    override fun onDismissDropDown() =
        updateState { screenState -> screenState.copy(dropDownExpanded = false) }

    override fun onBackClicked() = sendNewEffect(SearchByCountryScreenEffect.NavigatedBack)

    override fun onCountryNameChanged(countryName: TextFieldValue) {
        val updatedQuery = countryName.copy(selection = TextRange(countryName.text.length))
        updateState { screenState ->
            screenState.copy(
                countryName = updatedQuery,
                filteredCountries = filterCountriesByName(countryName),
                dropDownExpanded = countryName.text.isNotBlank() && state.value.filteredCountries.isNotEmpty()
            )
        }
    }

    override fun onCountryClicked(countryName: String) {
        updateScreenStateToLoading()
        tryToCall(
            call = { getCountryMoviesAsFlow(countryName) },
            onSuccess = ::updateScreenWithCountryMovies,
            onError = ::updateScreenWithError
        )
    }

    //endregion
    private fun filterCountriesByName(countryName: TextFieldValue): List<String> {
        return getCountriesNames().filter { country ->
            country.startsWith(countryName.text.trim(), ignoreCase = true)
        }
    }

    private fun getCountryMoviesAsFlow(countryName: String): Flow<PagingData<MovieUiState>> = Pager(
        config = defaultPageConfigurations(), pagingSourceFactory = {
            SearchMoviesByCountryNamePagingSource(
                countryName = countryName,
                searchMoviesByCountryUseCase = searchMoviesByCountryUseCase
            )
        }).flow.map { pagingData -> pagingData.map { movie -> movie.toMovieUiState() } }
        .cachedIn(viewModelScope)

    private fun updateScreenStateToLoading() {
        updateState { screenUiState ->
            screenUiState.copy(
                isLoading = true,
                isCountrySelected = true,
                dropDownExpanded = false,
                errorMessage = null,
            )
        }
    }

    private fun updateScreenWithCountryMovies(flowOfMoviesUiState: Flow<PagingData<MovieUiState>>) {
        updateState { screenState ->
            screenState.copy(
                isLoading = false, moviesOfCountryFlow = flowOfMoviesUiState
            )
        }
    }

    private fun updateScreenWithError(error: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorMessage = error.message, isLoading = false
            )
        }
    }
}