package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecase.GetSimilarMoviesUseCase
import usecase.GetSimilarSeriesUseCase

class MediaDetailsViewmodel(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarSeriesUseCase
) : ViewModel(), MediaInteractionListener {
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
        TODO("Not yet implemented")
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

    private val _similarMedia = MutableStateFlow<SimilarMediaUiState>(SimilarMediaUiState.Init)
    val similarMedia: StateFlow<SimilarMediaUiState> = _similarMedia.asStateFlow()

    override fun onShowMoreMediaLikeThisClicked(mediaId: Long, mediaType: MediaType) {
        viewModelScope.launch {
            _similarMedia.value = SimilarMediaUiState.Loading
            try {
                val similar = when (mediaType) {
                    MediaType.MOVIE -> getSimilarMoviesUseCase(mediaId).map { it.toUIStateMedia() }
                    MediaType.TV_SHOW -> getSimilarTVShowsUseCase(mediaId).map { it.toUIStateMedia() }
                }
                _similarMedia.value = SimilarMediaUiState.Success(similar)
            } catch (e: Exception) {
                _similarMedia.value =
                    SimilarMediaUiState.Error("Failed to load similar media: ${e.message}")
            }
        }
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

}