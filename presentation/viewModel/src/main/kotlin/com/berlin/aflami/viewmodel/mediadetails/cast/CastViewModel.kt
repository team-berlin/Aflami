package com.berlin.aflami.viewmodel.mediadetails.cast

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import kotlinx.coroutines.flow.update
import usecase.mediadetails.GetMovieCastUseCase
import usecase.mediadetails.GetSeriesCastUseCase

class CastViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetSeriesCastUseCase,
): BaseViewModel<MediaDetailsUiState, CastDetailsEffect>(
    MediaDetailsUiState()
) , CastDetailsListener {

    val id: Long = savedStateHandle.get<String>("id")?.toLongOrNull() ?: 0L
    val type: MediaType = savedStateHandle.get<String>("media_type")
        ?.let { MediaType.valueOf(it) } ?: MediaType.MOVIE

    init {

        getMediaCast(id,type)
    }

    fun getMediaCast(mediaId: Long, mediaType: MediaType) {
        _state.update {
            it.copy(error = null, isLoading = true)
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getMovieCastUseCase(mediaId).map { it.toUiState() }
                    MediaType.TVSHOW -> getSeriesCastUseCase(mediaId).map { it.toUiState() }
                }
            },
            onSuccess = { cast ->
                _state.update {
                    it.copy(
                        mediaCast = cast,
                        mediaType = mediaType,
                        isLoading = false
                    )
                }
            },
            onError = { errorUiState ->
                _state.update {
                    it.copy(
                        error = errorUiState.message
                    )
                }
            },
        )
    }

    override fun onCastBackClicked(){
        sendNewEffect(CastDetailsEffect.CastNavigationBack)

    }

}