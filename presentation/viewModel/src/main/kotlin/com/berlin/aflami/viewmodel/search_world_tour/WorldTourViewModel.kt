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
import repository.SearchRepository
import usecase.SearchByCountryUseCase

class WorldTourViewModel(
    private val searchByCountry:SearchByCountryUseCase

) : ViewModel() {

    private val _worldTourUiState = MutableStateFlow(WorldTourUiState())
    val worldTourUiState = _worldTourUiState.asStateFlow()


   fun getMovieByCountry(country: String) {

        _worldTourUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val movies= searchByCountry(country).map { movie ->
                movie.toUIState()
            }
            _worldTourUiState.update { it.copy(movies = movies, isLoading = false) }

        }
    }


}