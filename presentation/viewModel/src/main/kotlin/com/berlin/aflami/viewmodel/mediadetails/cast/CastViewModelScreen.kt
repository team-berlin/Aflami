package com.berlin.aflami.viewmodel.mediadetails.cast

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MovieDetailsScreenState
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
) : BaseViewModel<MovieDetailsScreenState, CastDetailsScreenEffect>(
    MovieDetailsScreenState()
), CastDetailsScreenListener {

    val mediaId: Long = castDetailsArgs.mediaId ?: 0
    val mediaType: MediaType = castDetailsArgs.mediaType ?: MediaType.MOVIE

    init {
        getMediaCast(mediaId, mediaType)
    }

    //region CastDetailsScreenListener implementation
    override fun onBackClicked() =
        sendNewEffect(CastDetailsScreenEffect.NavigationBack)
//endregion

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
//                isLoading = false,
//                actors = mediaActors,
//                error = null
            )
        }
    }

    private fun updateScreenStateToLoading() {
//        updateState { screenState ->
//           // screenState.copy(error = null, isLoading = true)
//        }
    }

    private fun updateScreenStateToError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
//                isLoading = false,
//                error = errorUiState.message
            )
        }
    }
}