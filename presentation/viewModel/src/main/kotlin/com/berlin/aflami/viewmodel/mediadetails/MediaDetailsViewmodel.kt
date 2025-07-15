package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecase.GetMovieCastUseCase
import usecase.GetSeriesCastUseCase

class MediaDetailsViewmodel(
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetSeriesCastUseCase
) : ViewModel(), MediaInteractionListener {

    private val _uiState = MutableStateFlow(MediaDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getMovieCast()
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
        TODO("Not yet ")
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


    private suspend fun getMovieCast() {
        _uiState.value = _uiState.value.copy(
            castState = getMovieCastUseCase(505, "ar-EG").map {
                MediaCastUiState(
                    mediaId = it.mediaId,
                    name = it.name,
                    poster = it.poster
                )
            })

    }

}