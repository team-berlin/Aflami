package com.berlin.aflami.viewmodel.searchactor

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toMediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import usecase.movie.SearchByActorNameUseCase
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class SearchByActorViewModel @Inject constructor(
    private val searchByActorNameUseCase: SearchByActorNameUseCase,
) : BaseViewModel<SearchByActorScreenState, SearchByActorScreenEffect>(SearchByActorScreenState()),
    SearchByActorInteractionListener {

    init {
        observeQuery()
    }

    //region SearchByActorInteractionListener implementation
    override fun onBackClicked() = sendNewEffect(SearchByActorScreenEffect.NavigatedBack)

    override fun onMediaCardClicked(movieId: Long, mediaType: MediaType) = sendNewEffect(
        SearchByActorScreenEffect.NavigatedToMediaDetailsScreen(movieId, mediaType)
    )

    override fun onActorNameChanged(actorName: TextFieldValue) =
        updateState { screenState -> screenState.copy(actorName = actorName) }

    //endregion
    private fun observeQuery() {
        viewModelScope.launch {
            _state.map { screenState ->
                screenState.actorName
            }.debounce(600)
                .filter { textFieldValue -> textFieldValue.text.isNotEmpty() }
                .distinctUntilChanged()
                .collect { searchMoviesByActorName(actorName = it.text) }
        }
    }

    private fun searchMoviesByActorName(actorName: String) {
        updateScreenStateToLoading()
        tryToCall(
            call = { getActorMediaContributionsAsFlow(actorName) },
            onSuccess = ::onSearchSuccess,
            onError = ::updateScreenStateWithError
        )
    }

    fun getActorMediaContributionsAsFlow(actorName: String): Flow<PagingData<MediaUiState>> =
        Pager(
            config = defaultPageConfigurations(),
            pagingSourceFactory = {
                SearchByActorNamePagingSource(actorName, searchByActorNameUseCase)
            }
        ).flow
            .map { pagingData ->
                pagingData.map { movie -> movie.toMediaUiState() }
            }.cachedIn(viewModelScope)

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(isLoading = true) }

    private fun onSearchSuccess(mediaFlow: Flow<PagingData<MediaUiState>>) =
        updateState { screenState ->
            screenState.copy(
                mediaPagingDataFlow = mediaFlow,
                isLoading = false
            )
        }

    fun updateScreenStateWithError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorUiState = errorUiState,
                isLoading = false
            )
        }
    }
}