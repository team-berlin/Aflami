package com.berlin.aflami.viewmodel.watchedmedia

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.watchedmedia.uistate.WatchedMediaUiState
import kotlinx.coroutines.flow.update
import usecase.home.GetWatchedMediaUseCase

class WatchedMediaViewModel(
    private val getWatchedMediaUseCase: GetWatchedMediaUseCase
) : BaseViewModel<WatchedMediaUiState, WatchedMediaEffect>(
    WatchedMediaUiState()
), WatchedMediaInteractionListener {

    init {
        getContinueWatchingMedia()
    }

    override fun onBackClicked() {
        sendNewEffect(WatchedMediaEffect.onBackClicked)
    }

    override fun onMediaCardClick(id: Long, type: MediaType) {
        sendNewEffect(WatchedMediaEffect.NavigateToDetails(id, type))

    }

    private fun getContinueWatchingMedia() {
        _state.update {
            it.copy(isLoading = true, error = null)
        }
        tryToCall(
            call = {
                getWatchedMediaUseCase().map { continueWatchingCards ->
                    continueWatchingCards.toUIState()
                }

            },
            onSuccess = {continueWatchingMedia->
                _state.update {
                    it.copy(
                        continueWatchingMedia = continueWatchingMedia,
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