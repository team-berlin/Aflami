package com.berlin.aflami.viewmodel.home.continueWatching

import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase
import javax.inject.Inject

@HiltViewModel
class ContinueWatchingMediaViewModel @Inject constructor(
    private val getContinueWatchingMoviesUseCase: ContinueWatchingMovieUseCase,
    private val getContinueWatchingTVShowsUseCase: ContinueWatchingTVShowUseCase,
) : BaseViewModel<ContinueWatchingScreenState, ContinueWatchingScreenEffect>(
    ContinueWatchingScreenState()
), ContinueWatchingMediaInteractionListener {

    init {
        getContinueWatchingMedia()
    }
    //region continueWatchingInteractionListener implementation
    override fun onBackClicked() = sendNewEffect(ContinueWatchingScreenEffect.NavigateBack)

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(ContinueWatchingScreenEffect.NavigateToDetailsScreen(mediaId, mediaType))
    //endregion
    private fun getContinueWatchingMedia() {
        updateScreenStateToLoading()
        tryToCall(
            call = { getContinueWatchingMediaAsFlow() },
            onSuccess = ::updateScreenStateWithNewMedia,
            onError = ::updateScreenStateToError
        )
    }

    private fun updateScreenStateWithNewMedia(continueWatchingMedia: Flow<PagingData<MediaUiState>>) =
        updateState { screenState ->
            screenState.copy(continueWatchingMediaFlow = continueWatchingMedia, isLoading = false)
        }

    private fun getContinueWatchingMediaAsFlow(): Flow<PagingData<MediaUiState>> = Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            ContinueWatchingMediaPagingSource(
                getContinueWatchingMoviesUseCase,
                getContinueWatchingTVShowsUseCase
            )
        }
    ).flow

    private fun updateScreenStateToError(errorUiState: ErrorUiState) =
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorUiState.message,
                isLoading = false
            )
        }

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(isLoading = true) }

}