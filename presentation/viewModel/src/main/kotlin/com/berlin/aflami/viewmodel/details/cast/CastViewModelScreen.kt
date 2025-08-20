package com.berlin.aflami.viewmodel.details.cast

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import usecase.movie.GetMovieCastUseCase
import usecase.tvshow.GetTVShowCastUseCase
import javax.inject.Inject

@HiltViewModel
class CastViewModelScreen @Inject constructor(
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetTVShowCastUseCase,
    castDetailsArgs: CastDetailsArgs,
) : BaseViewModel<CastScreenState, CastDetailsScreenEffect>(
    CastScreenState()
), CastDetailsScreenListener {

    val mediaId: Long =
        castDetailsArgs.mediaId ?: throw IllegalArgumentException("Media id is null")
    val mediaType: MediaType =
        castDetailsArgs.mediaType ?: throw IllegalArgumentException("Media type is null")

    init {
        mediaId
        getMediaCast(mediaId, mediaType)
    }

    override fun onBackClicked() =
        sendNewEffect(CastDetailsScreenEffect.NavigationBack)

    fun getMediaCast(mediaId: Long, mediaType: MediaType) {
        updateScreenStateToLoading()
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getMovieCastUseCase(mediaId).map { cast -> cast.toActorUiState() }
                    MediaType.TV_SHOW -> getSeriesCastUseCase(mediaId).map { it.toActorUiState() }
                }
            },
            onSuccess = ::updateScreenWithMediaActors,
            onError = ::updateScreenStateToError
        )
    }

    private fun updateScreenWithMediaActors(
        mediaActors: List<ActorUiState>,
    ) {
        updateState { screenState ->
            screenState.copy(
                castList = mediaActors,
                isScreenLoading = false,
                errorUiState = null
            )
        }
    }

    private fun updateScreenStateToLoading() {
        updateState { screenState ->
            screenState.copy(errorUiState = null, isScreenLoading = true)
        }
    }

    private fun updateScreenStateToError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                isScreenLoading = false,
                errorUiState = errorUiState
            )
        }
    }
}