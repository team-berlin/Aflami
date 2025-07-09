package com.berlin.aflami.viewmodel.search_world_tour

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.SearchByCountryUseCase

class WorldTourViewModel(
    private val searchByCountry:SearchByCountryUseCase

) : ViewModel(),WorldTourInteractionListener {

    private val _uiState = MutableStateFlow(WorldTourUiState())
    val uiState = _uiState.asStateFlow()

//
//   fun getMovieByCountry(country: String) {
//
//        _worldTourUiState.update { it.copy(isLoading = true) }
//        viewModelScope.launch(Dispatchers.IO) {
//            val movies= searchByCountry(country).map { movie ->
//                movie.toUIState()
//            }
//            _worldTourUiState.update { it.copy(movies = movies, isLoading = false) }
//
//        }
//    }

    override fun onBackClick() {
//        TODO("Not yet implemented")
    }

    override fun onCountryNameChanged(countryName: CharSequence) {
        _uiState.update { it.copy(countryName = countryName.toString()) }
    }

    override fun onSearchClick() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                // TODO: Country name need to be mapped to country code
                val result = searchByCountry(uiState.value.countryName).map {
                    it.toUIState()
                }
                onSearchSuccess(result)
                Log.e("result",result.toString())
            } catch (exception: Exception) {
                // TODO: msg resId
                Log.e("error",exception.toString())

                onSearchError(exception.message ?: "Unknown error")
            }
        }
    }

    override fun onClickMovie(id: Int) {
//        TODO("Not yet implemented")
    }

    private fun onSearchSuccess(movies: List<MovieUIState>) {

        _uiState.update { it.copy(movies = movies, isLoading = false) }
    }

    private fun onSearchError(message: String) {
        _uiState.update { it.copy(error = message, isLoading = false) }

    }




}