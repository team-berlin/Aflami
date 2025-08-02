package com.berlin.aflami.viewmodel.home.toprating

import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.tvshow.GetTopRatedTVShowUseCase

class TopRatingViewModel(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTVShowUseCase,
) : BaseViewModel<TopRatingScreenState, TopRatingScreenEffect>(TopRatingScreenState()),
    TopRatingInteractionListener {

    init {
        getTopRatingMedia()
    }

    override fun onBackClicked() = sendNewEffect(TopRatingScreenEffect.NavigateBack)

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(TopRatingScreenEffect.NavigateToMediaDetailsScreen(mediaId, mediaType))

    private fun getTopRatingMedia() {
        updateScreenStateToLoading()
        tryToCall(
            call = { getTopRatedMediaAsFlow(getTopRatedMoviesUseCase, getTopRatedTvShowsUseCase) },
            onSuccess = ::updateScreenStateWithNewTopRatedMedia,
            onError = ::updateScreenStateToError
        )
    }

    private fun getTopRatedMediaAsFlow(
        getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
        getTopRatedTvShowsUseCase: GetTopRatedTVShowUseCase,
    ): Flow<PagingData<MediaUiState>> = Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            TopRatingMoviesPagingSource(
                getTopRatedMoviesUseCase,
                getTopRatedTvShowsUseCase
            )
        }
    ).flow

    private fun updateScreenStateWithNewTopRatedMedia(topRatingMediaFlow: Flow<PagingData<MediaUiState>>) {
        _state.update {
            it.copy(
                topRatedMediaFlow = topRatingMediaFlow,
                isLoading = false,
            )
        }
    }

    private fun updateScreenStateToError(errorUiState: ErrorUiState) =
        updateState { it.copy(errorMessage = errorUiState.message) }

    private fun updateScreenStateToLoading() = updateState { it.copy(isLoading = true) }

}