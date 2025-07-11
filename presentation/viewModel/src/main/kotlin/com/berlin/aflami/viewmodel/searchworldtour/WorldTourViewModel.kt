package com.berlin.aflami.viewmodel.searchworldtour

import android.util.Log
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

    lateinit var countriesWithCodeMap: Map<String, String>

    init {
        getCountriesWithCode()
    }

    private fun getCountriesWithCode() {
        val countriesCodes = Locale.getISOCountries()
        val countriesWithCode = mutableMapOf<String, String>()
        for (countryCode in countriesCodes) {
            val locale = Locale("", countryCode)
            countriesWithCode[locale.displayCountry] = countryCode
        }
        countriesWithCodeMap = countriesWithCode.toMap()
    }

    override fun onBackClick() {
        // TODO: ("Not yet implemented")
    }

    override fun onCountryNameChanged(countryName: CharSequence) {
        val name = countryName.toString()
        val filtered = countriesWithCodeMap.filter {
            it.key.startsWith(name, ignoreCase = true)
        }

        _uiState.update {
            it.copy(
                countryName = countryName.toString(),
                filteredCountries = filtered,
                dropDownExpanded = name.isNotEmpty() && filtered.isNotEmpty()
            )
        }
    }

    override fun onCountrySelected(countryName: String) {
        _uiState.update { it.copy(countryName = countryName) }
    }

    override fun onCountrySelected() {
        _uiState.update {
            it.copy(
                isLoading = true,
                dropDownExpanded = false
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val countryName = countriesWithCodeMap[_uiState.value.countryName]
            if (countryName == null) {
                onSearchError("Invalid country name") // Todo:
                return@launch
            }
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val result = searchByCountry(countryName, languageCode).map { it.toUIState() }
                Log.e("WorldTourViewModel", result.toString())
                onSearchSuccess(result)
            } catch (exception: Exception) {
                // TODO: msg resId
                Log.e("WorldTourViewModel", exception.message ?: "Unknown error")
                onSearchError(exception.message ?: "Unknown error")
            }
        }
    }

    override fun onMovieClick(id: Int) {
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