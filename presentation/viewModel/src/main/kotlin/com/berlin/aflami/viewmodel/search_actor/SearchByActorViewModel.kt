package com.berlin.aflami.viewmodel.search_actor

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
import usecase.SearchByActorNameUseCase
import java.util.Locale

class SearchByActorViewModel(
    private val searchByActorName: SearchByActorNameUseCase
) : ViewModel(), SearchByActorInteractionListener {

    private val _uiState = MutableStateFlow(SearchByActorScreenUiState())
    val uiState = _uiState.asStateFlow()

    init { }

    override fun onBackClick() {
        //TODO("Not yet implemented")
    }

    override fun onActorNameChanged(actorName: CharSequence) {
        _uiState.update { it.copy(actorName = actorName.toString()) }

    }

    override fun onSearchClick() {
        Log.d("findByActor", "onSearchClick")
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val result = searchByActorName(
                    actorName = _uiState.value.actorName,
                    language = languageCode
                ).map { it.toUIState() }
                Log.i("findByActor", result.toString())
                onSearchSuccess(result)
            } catch (exception: Exception) {
                // TODO:
                Log.i("findByActorError", exception.message ?: "" )
                onSearchError(exception.message ?: "Unknown error")
            }
        }
    }

    private fun onSearchSuccess(movies: List<MovieUIState>) {
        _uiState.update { it.copy(movies = movies, isLoading = false) }
    }

    private fun onSearchError(message: String) {
        _uiState.update { it.copy(error = message, isLoading = false) }
    }

    override fun onClickMovie(id: Int) {
        //TODO("Not yet implemented")
    }
}