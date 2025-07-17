package com.berlin.aflami.viewmodel.search_actor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.searchworldtour.BasePagingSource
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.SearchByActorNameUseCase
import java.util.Locale

@OptIn(FlowPreview::class)
class SearchByActorViewModel(
    private val searchByActorName: SearchByActorNameUseCase
) : ViewModel(), SearchByActorInteractionListener {

    private val _uiState = MutableStateFlow(SearchByActorScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow

    init {
        viewModelScope.launch {
            _queryFlow
                .debounce(600)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .collect {
                    onSearchClicked()
                }
        }
    }



    override fun onActorNameChanged(actorName: CharSequence) {
        _queryFlow.update { actorName.toString() }
        _uiState.update { it.copy(actorName = actorName.toString()) }
    }

    override fun onSearchClicked() {

        _uiState.update { it.copy(isLoading = true) }
        searchMovies()
    }

    private fun searchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val movies = Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        initialLoadSize = 20
                    ),
                    pagingSourceFactory = {
                        BasePagingSource { page ->
                            searchByActorName(
                                actorName = uiState.value.actorName,
                                page
                            )
                        }
                    },
                ).flow.map {
                    it.map { it.toUIState() }
                }.cachedIn(viewModelScope)
                onSearchSuccess(movies)
            } catch (exception: Exception) {
                onSearchError(exception.message ?: "Unknown error")
            }
        }

    }

    private fun onSearchSuccess(movies: Flow<PagingData<MovieUIState>>) {
        _uiState.update { it.copy(movies = movies, isLoading = false) }
    }

    private fun onSearchError(message: String) {
        _uiState.update { it.copy(error = message, isLoading = false) }
    }


}