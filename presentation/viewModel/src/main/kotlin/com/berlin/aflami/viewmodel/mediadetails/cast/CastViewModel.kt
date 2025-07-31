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
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetSeriesCastUseCase,
    val id:Long,
    val type: MediaType
): BaseViewModel<MediaDetailsUiState, CastDetailsEffect>(
    MediaDetailsUiState()
) , CastDetailsListener {

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