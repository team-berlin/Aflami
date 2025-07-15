package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.uistate.MediaDetailsScreenUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetMovieCastUseCase
import usecase.GetSeriesCastUseCase

class MediaDetailsViewmodel(
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetSeriesCastUseCase
) : ViewModel(), MediaInteractionListener {

    private val _uiState = MutableStateFlow(MediaDetailsScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _castDetailsNavigationState = MutableSharedFlow<Long>(replay = 0)
    val castDetailsNavigationState = _castDetailsNavigationState.asSharedFlow()

    init {
        viewModelScope.launch {
            getMovieCast(505,MediaType.MOVIE,"ar-EG")
        }
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }

    override fun onPlayClicked(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onReadMoreDescriptionClicked(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onShowCastClicked(id: Long) {
        viewModelScope.launch {
            _castDetailsNavigationState.emit(id)
        }
    }

    override fun onRateIconClicked(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onSelectRateClicked(rate: Float) {
        TODO("Not yet implemented")
    }

    override fun onSubmitRateClicked(rate: Float) {
        TODO("Not yet implemented")
    }

    override fun onCancelRatingClicked() {
        TODO("Not yet implemented")
    }

    override fun onAddMediaToFavouriteListClicked(
        favouriteListId: Int,
        mediaId: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onSelectFavouriteList(favouriteListId: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateNewFavouriteListClicked() {
        TODO("Not yet implemented")
    }

    override fun onCancelAddingToFavouriteClicked() {
        TODO("Not yet implemented")
    }

    override fun onUpdateNewListTitle(newListTitle: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateNewListClicked(listTitle: String) {
        TODO("Not yet implemented")
    }

    override fun onCancelCreatingNewListClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowMoreMediaLikeThisClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowReviewsClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowMediaGalleryClicked(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onShowCompanyProductionClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllSeasonsClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowSeasonEpisodesClicked(tvShowId: Long, seasonId: Long) {
        TODO("Not yet implemented")
    }

    override fun onHideSeasonEpisodesClicked(seasonId: Long) {
        TODO("Not yet implemented")
    }


    private fun getMovieCast(mediaId: Long, mediaType: MediaType,language:String) {
        viewModelScope.launch {
            val cast = when (mediaType) {
                MediaType.MOVIE -> getMovieCastUseCase(mediaId, language).map {
                    MediaCastUiState(
                        mediaId = it.mediaId,
                        name = it.name,
                        poster = it.poster
                    )
                }

                MediaType.TV_SHOW -> getSeriesCastUseCase(mediaId, language).map {
                    MediaCastUiState(
                        mediaId = it.mediaId,
                        name = it.name,
                        poster = it.poster
                    )
                }
            }
            _uiState.update { newCastState ->
                newCastState.copy(
                    mediaCast = cast,
                    mediaType = mediaType
                )
            }
        }
    }

}
