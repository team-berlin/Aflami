package com.berlin.aflami.viewmodel.search_world_tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.SearchByCountryUseCase
import java.util.Locale

class WorldTourViewModel(
    private val searchByCountry: SearchByCountryUseCase
) : ViewModel(), WorldTourInteractionListener {

    private val _uiState = MutableStateFlow(WorldTourUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCountriesWithCode()
    }

    private fun getCountriesWithCode() {
        val countries = Locale.getISOCountries()
        val countriesWithCode = mutableMapOf<String, String>()
        for (country in countries) {
            val locale = Locale("", country)
            countriesWithCode[locale.displayCountry] = country
        }
        _uiState.update { it.copy(countriesWithCode = countriesWithCode) }
    }

    override fun onBackClick() {
        // TODO: ("Not yet implemented")
    }

    override fun onCountryNameChanged(countryName: CharSequence) {
        val name = countryName.toString()
        val filtered = _uiState.value.countriesWithCode.filter {
            it.key.startsWith(name, ignoreCase = true)
        }

        _uiState.update {
            it.copy(
                countryName = name,
                filteredCountries = filtered,
                dropDownExpanded = name.isNotEmpty() && filtered.isNotEmpty()
            )
        }
    }

    override fun onCountrySelected(countryName: String) {
        _uiState.update { it.copy(countryName = countryName) }
    }

    override fun onSearchClick() {
        _uiState.update {
            it.copy(
                isLoading = true,
                dropDownExpanded = false
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val countryName = _uiState.value.countriesWithCode[_uiState.value.countryName]
            if (countryName == null) {
                onSearchError("Invalid country name") // Todo:
                return@launch
            }
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val result = searchByCountry(
                    countryName = countryName,
                    language = languageCode
                ).map {
                    it.toUIState()
                }
                onSearchSuccess(result)
            } catch (exception: Exception) {
                // TODO: msg resId
                onSearchError(exception.message ?: "Unknown error")
            }
        }
    }

    override fun onClickMovie(id: Int) {
        // TODO("Not yet implemented")
    }

    override fun onDismissDropDown() {
        _uiState.update { it.copy(dropDownExpanded = false) }
    }

    private fun onSearchSuccess(movies: List<MovieUIState>) {
        _uiState.update { it.copy(movies = movies, isLoading = false) }
    }

    private fun onSearchError(message: String) {
        _uiState.update { it.copy(error = message, isLoading = false) }
    }
}