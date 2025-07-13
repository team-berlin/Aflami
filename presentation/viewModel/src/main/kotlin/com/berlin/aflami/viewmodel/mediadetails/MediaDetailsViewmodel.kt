package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.ViewModel

class MediaDetailsViewmodel : ViewModel(), MediaInteractionListener {
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

    override fun onShowMoreMediaLikeThisClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowReviewsClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowMediaGalleryClicked() {
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