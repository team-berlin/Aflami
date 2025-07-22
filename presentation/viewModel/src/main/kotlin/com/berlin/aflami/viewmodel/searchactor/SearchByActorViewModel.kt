package com.berlin.aflami.viewmodel.searchactor

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.util.MediaType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.SearchByActorNameUseCase

@OptIn(FlowPreview::class)
class SearchByActorViewModel(
    private val searchByActorName: SearchByActorNameUseCase
) : BaseViewModel<SearchByActorScreenUiState, SearchByActorEffect>(SearchByActorScreenUiState()),
    SearchByActorInteractionListener {

    init {
        observeQuery()
    }

    private fun observeQuery() {
        viewModelScope.launch {
            _state.map {
                it.query
            }.debounce(600).filter { it.isNotEmpty() }.distinctUntilChanged()
                .collect { searchMovies() }
        }
    }

    override fun onMovieClicked(movieId: Long, mediaType: MediaType) {
        sendNewEffect(SearchByActorEffect.NavigatedToMediaDetailsScreen(movieId, mediaType.name))
    }

    override fun onActorNameChanged(actorName: CharSequence) {
        _state.update { it.copy(query = actorName.toString()) }
    }

    override fun onBackClicked() {
        sendNewEffect(SearchByActorEffect.NavigatedBack)
    }

    private fun searchMovies() {
        _state.update { it.copy(isLoading = true) }
        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20, initialLoadSize = 20
                    ),
                    pagingSourceFactory = {
                        BasePagingSource { page ->
                            searchByActorName(actorName = _state.value.query, page = page)
                        }
                    },
                ).flow.map {
                    it.map { it.toUIState() }
                }.cachedIn(viewModelScope)
            }, onSuccess = ::onSearchSuccess, onError = ::onSearchError
        )
    }

    private fun onSearchSuccess(movies: Flow<PagingData<MediaUiState>>) {
        _state.update { it.copy(movies = movies, isLoading = false) }
    }

    private fun onSearchError(throwable: Throwable) {
        _state.update { it.copy(error = throwable.message, isLoading = false) }
    }
}