package com.berlin.aflami.viewmodel.home.continueWatching

import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase

class ContinueWatchingMediaViewModel(
    private val getContinueWatchingMoviesUseCase: ContinueWatchingMovieUseCase,
    private val getContinueWatchingTVShowsUseCase: ContinueWatchingTVShowUseCase,
) : BaseViewModel<ContinueWatchingScreenState, ContinueWatchingScreenEffect>(
    ContinueWatchingScreenState()
), ContinueWatchingMediaInteractionListener {

    init {
        getContinueWatchingMedia()
    }

    override fun onBackClicked() = sendNewEffect(ContinueWatchingScreenEffect.NavigateBack)

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(ContinueWatchingScreenEffect.NavigateToDetails(mediaId, mediaType))

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