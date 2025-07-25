package com.berlin.aflami.viewmodel.watchedmedia

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.watchedmedia.uistate.ContinueWatchingMediaUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
        sendNewEffect(ContinueWatchingMediaEffect.onBackClicked)
    }

    override fun onMediaCardClick(id: Long, type: MediaType) {
        sendNewEffect(ContinueWatchingMediaEffect.NavigateToDetails(id, type))

    }

    private fun getContinueWatchingMedia() {
        _state.update {
            it.copy(isLoading = true, error = null)
        }
        tryToCall(
            call = {
                coroutineScope {
                    val moviesList = async { getWatchedMovieUseCase().map { it.toUIStateMedia() } }
                    val tvShowsList =
                        async { getWatchedTVShowUseCase().map { it.toUIStateMedia() } }

                    val movies = moviesList.await()
                    val tvShows = tvShowsList.await()
                    
                    val combinedList = (movies + tvShows)
                        .shuffled()
                    combinedList
                }
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