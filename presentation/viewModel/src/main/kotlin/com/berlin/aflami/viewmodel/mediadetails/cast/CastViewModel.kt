package com.berlin.aflami.viewmodel.mediadetails.cast

import com.berlin.aflami.viewmodel.CastDetailsArgs
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import usecase.mediadetails.GetMovieCastUseCase
import usecase.mediadetails.GetSeriesCastUseCase
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetSeriesCastUseCase,
    castDetailsArgs: CastDetailsArgs,
) : BaseViewModel<MediaDetailsUiState, CastDetailsEffect>(
    MediaDetailsUiState()
), CastDetailsListener {

    val mediaId: Long = castDetailsArgs.mediaId ?: 0
    val mediaType: MediaType = castDetailsArgs.mediaType ?: MediaType.MOVIE

    init {
        getMediaCast(mediaId, mediaType)
    }

    fun getMediaCast(mediaId: Long, mediaType: MediaType) {
        _state.update {
            it.copy(error = null, isLoading = true)
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getMovieCastUseCase(mediaId).map { it.toUiState() }
                    MediaType.TV_SHOW -> getSeriesCastUseCase(mediaId).map { it.toUiState() }
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

    override fun onCastBackClicked() {
        sendNewEffect(CastDetailsEffect.CastNavigationBack)

    }

}