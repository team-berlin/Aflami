package com.berlin.aflami.viewmodel.watchedmedia

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase

class ContinueWatchingMediaViewModel(
    private val getWatchedMovieUseCase: GetContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: GetContinueWatchingTVShowUseCase
) : BaseViewModel<ContinueWatchingMediaUiState, ContinueWatchingMediaEffect>(
    ContinueWatchingMediaUiState()
), ContinueWatchingMediaInteractionListener {

    init {
        viewModelScope.launch {
            getContinueWatchingMedia()
        }
    }

    override fun onBackClicked() {
        sendNewEffect(ContinueWatchingMediaEffect.OnBackClicked)
    }

    override fun onMediaCardClicked(id: Long, type: MediaType) {
        sendNewEffect(ContinueWatchingMediaEffect.NavigateToDetails(id, type))

    }

    private fun getContinueWatchingMedia() {
        _state.update {
            it.copy(isLoading = true, error = null)
        }
        tryToCall(
            call = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20, initialLoadSize = 20
                    ),
                    pagingSourceFactory = {
                        BasePagingSource { page ->
                            coroutineScope {
                                val moviesList =
                                    async { getWatchedMovieUseCase(page).map { it.toUIStateMedia() } }
                                val tvShowsList =
                                    async { getWatchedTVShowUseCase(page).map { it.toUIStateMedia() } }

                                val movies = moviesList.await()
                                val tvShows = tvShowsList.await()

                                val combinedList = (movies + tvShows).shuffled()
                                combinedList
                            }
                        }
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { continueWatchingMedia ->
                _state.update {
                    it.copy(
                        continueWatchingItems = continueWatchingMedia,
                        isLoading = false,
                    )
                }

            },
            onError = { throwable ->
                _state.update {
                    it.copy(
                        error = throwable.message
                    )
                }
            },
        )
    }
}