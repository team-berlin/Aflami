package com.berlin.aflami.viewmodel.searchworldtour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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
        val countryName = _uiState.value.countryName
        val countryCode = countriesWithCodeMap[countryName]

        if (countryCode != null) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    dropDownExpanded = false,
                    error = null
                )
            }

            viewModelScope.launch(Dispatchers.IO) {
                val movies = Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        initialLoadSize = 20
                    ),
                    pagingSourceFactory = {
                        BasePagingSource { page ->
                            searchByCountry.invoke(
                                query = countriesWithCodeMap[uiState.value.countryName].toString(),
                                page = page
                            )
                        }
                    },
                ).flow.map {
                    it.map { it.toUIState() }
                }.cachedIn(viewModelScope)
                onSearchSuccess(movies)
            }
        } else {
            _uiState.update {
                it.copy(error = "Invalid country selected.")
            }
        }
    }

    override fun onDismissDropDown() {
        _uiState.update { it.copy(dropDownExpanded = false) }
    }

    private fun onSearchSuccess(movies: Flow<PagingData<MovieUIState>>) {
        _uiState.update { it.copy(isLoading = false, movies = movies) }
    }

    private fun onSearchError(message: String) {
        _uiState.update { it.copy(error = message, isLoading = false) }
    }
}