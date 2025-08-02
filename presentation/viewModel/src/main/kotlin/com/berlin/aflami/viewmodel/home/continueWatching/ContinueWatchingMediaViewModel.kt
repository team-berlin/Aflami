package com.berlin.aflami.viewmodel.home.continueWatching

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase

class ContinueWatchingMediaViewModel(
    private val getContinueWatchingMoviesUseCase: ContinueWatchingMovieUseCase,
    private val getContinueWatchingTVShowsUseCase: ContinueWatchingTVShowUseCase,
) : BaseViewModel<ContinueWatchingMediaUiState, ContinueWatchingScreenEffect>(
    ContinueWatchingMediaUiState()
), ContinueWatchingMediaInteractionListener {

    init {
        viewModelScope.launch {
            getContinueWatchingMedia()
        }
    }

    override fun onBackClicked() = sendNewEffect(ContinueWatchingScreenEffect.NavigateBack)

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(ContinueWatchingScreenEffect.NavigateToDetails(mediaId, mediaType))

    private fun getContinueWatchingMedia() {
        _state.update {
            it.copy(isLoading = true, errorMessage = null)
        }
        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(
                        pageSize = BasePagingSource.PAGE_SIZE,
                        initialLoadSize = BasePagingSource.PAGE_SIZE
                    ),
                    pagingSourceFactory = {
                        ContinueWatchingMediaPagingSource(
                            getContinueWatchingMoviesUseCase,
                            getContinueWatchingTVShowsUseCase
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { continueWatchingMedia ->
                _state.update {
                    it.copy(
                        continueWatchingMediaFlow = continueWatchingMedia,
                        isLoading = false,
                    )
                }

            },
            onError =
                { errorUiState ->
                    _state.update {
                        it.copy(
                            errorMessage = errorUiState.message
                        )
                    }
                },
        )
    }
}