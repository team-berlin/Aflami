package com.berlin.aflami.viewmodel.search_by_actor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.SearchByActorNameUseCase

class SearchByActorViewModel(
    private val searchByActorNameUseCase: SearchByActorNameUseCase

) : ViewModel(), SearchByNameInteractor {

    private val _searchByActorUiState = MutableStateFlow(SearchByActorUiState())
    val searchByActorUiState = _searchByActorUiState.asStateFlow()

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

    }

    override fun onActorNameChanged(actorName: CharSequence) {
        _searchByActorUiState.update { it.copy(actorName = actorName.toString()) }
    }

    override fun onSearchClick() {
        _searchByActorUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val result = searchByActorNameUseCase(searchByActorUiState.value.actorName).map {
                    when(this){
                        is Movie -> it.toUIState()
                        is TvShow -> it.toUIState()
                    }
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

        _searchByActorUiState.update { it.copy(media = movies, isLoading = false) }
    }

    private fun onSearchError(message: String) {
        _searchByActorUiState.update { it.copy(error = message, isLoading = false) }

    }




}

private fun MediaTypeEntity.toUIState() {
    TODO("Not yet implemented")
}
