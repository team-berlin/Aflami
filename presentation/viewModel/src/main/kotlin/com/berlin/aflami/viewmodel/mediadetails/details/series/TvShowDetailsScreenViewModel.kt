package com.berlin.aflami.viewmodel.mediadetails.details.series

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import usecase.mediadetails.GetTVShowVideos
import usecase.tvshow.AddContinueWatchingTVShowUseCase
import usecase.tvshow.GetSeasonEpisodesUseCase
import usecase.tvshow.GetSimilarTVShowsUseCase
import usecase.tvshow.GetTVShowCastUseCase
import usecase.tvshow.GetTVShowDetailsUseCase
import usecase.tvshow.GetTVShowGalleryUseCase
import usecase.tvshow.GetTVShowReviewUseCase

class TvShowDetailsScreenViewModel(
    private val getTvShowDetailsUseCase: GetTVShowDetailsUseCase,
    private val getSeriesCastUseCase: GetTVShowCastUseCase,
    private val getSeriesGalleryUseCase: GetTVShowGalleryUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarTVShowsUseCase,
    private val seriesReviewUseCase: GetTVShowReviewUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val addContinueWatchingTVShowUseCase: AddContinueWatchingTVShowUseCase,
    private val getTvShowVideos: GetTVShowVideos,
) :
    BaseViewModel<TvShowDetailsUiState, TvShowDetailsScreenEffect>(TvShowDetailsUiState()),
    TvShowDetailsScreenInteractionListener {

    override fun onSeasonsClicked(seriesId: Long, numberOfSeasons: Int) {
        TODO("Not yet implemented")
    }

    override fun onShowMoreMediaLikeThisClicked(
        mediaId: Long,
        mediaType: MediaType,
    ) {
        TODO("Not yet implemented")
    }

    override fun onShowReviewsClicked(
        mediaId: Long,
        mediaType: MediaType,
    ) {
        TODO("Not yet implemented")
    }

    override fun onShowMediaGalleryClicked(
        mediaId: Long,
        mediaType: MediaType,
    ) {
        TODO("Not yet implemented")
    }

    override fun onShowCompanyProductionClicked() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }

    override fun onPlayClicked(
        mediaId: Long,
        mediaType: MediaType,
    ) {
        TODO("Not yet implemented")
    }

    override fun onReadMoreDescriptionClicked() {
        TODO("Not yet implemented")
    }

    override fun onReadMoreReviewClicked(reviewId: String) {
        TODO("Not yet implemented")
    }

    override fun onShowCastClicked() {
        TODO("Not yet implemented")
    }

    override fun onMediaClicked(
        mediaId: Long,
        mediaType: MediaType,
    ) {
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
        mediaId: Int,
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


}