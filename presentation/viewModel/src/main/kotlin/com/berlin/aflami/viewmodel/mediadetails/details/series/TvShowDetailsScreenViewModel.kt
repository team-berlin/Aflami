package com.berlin.aflami.viewmodel.mediadetails.details.series

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.tvShowToUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.UiText
import com.berlin.viewModel.R
import usecase.mediadetails.GetTVShowVideos
import usecase.tvshow.AddContinueWatchingTVShowUseCase
import usecase.tvshow.GetSeasonEpisodesUseCase
import usecase.tvshow.GetSimilarTVShowsUseCase
import usecase.tvshow.GetTVShowCastUseCase
import usecase.tvshow.GetTVShowDetailsUseCase
import usecase.tvshow.GetTVShowGalleryUseCase
import usecase.tvshow.GetTVShowReviewUseCase

class TvShowDetailsScreenViewModel(
    private val getTVShowDetailsUseCase: GetTVShowDetailsUseCase,
    private val getTVShowCastUseCase: GetTVShowCastUseCase,
    private val getTVShowGalleryUseCase: GetTVShowGalleryUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarTVShowsUseCase,
    private val tvShowReviewUseCase: GetTVShowReviewUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val addContinueWatchingTVShowUseCase: AddContinueWatchingTVShowUseCase,
    private val getTVShowVideos: GetTVShowVideos,
) :
    BaseViewModel<TVShowDetailsUiState, TvShowDetailsScreenEffect>(TVShowDetailsUiState()),
    TvShowDetailsScreenInteractionListener {

    override fun onSeasonsClicked(tvShowId: Long, numberOfSeasons: Int) {
        TODO("Not yet implemented")
    }

    override fun onShowMoreMediaLikeThisClicked(
        tvShowId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getSimilarTVShowsUseCase(tvShowId = tvShowId).map { tVShow -> tVShow.tvShowToUiState() }
            },
            onSuccess = { moreLikeThisTVShowList ->
                if (moreLikeThisTVShowList.isEmpty()) {
                    updateState { screenState ->
                        screenState.copy(
                            rowSection = TVShowRowSectionUiState.NoDataFound(
                                UiText.Resource(
                                    NO_MORE_MEDIA
                                )
                            )
                        )
                    }
                } else {
                    updateState {
                        it.copy(
                            rowSection = RowSectionUiState.Success(
                                content = TabContent.MoreLikeThis(
                                    items = moreLikeThisTVShowList
                                )
                            )
                        )
                    }
                }

            },
            onError = { errorState ->
                handleErrorState(
                    errorState,
                    updateRowSection = true
                )
            },
        )
    }

    override fun onShowReviewsClicked(
        mediaId: Long,
    ) {
        TODO("Not yet implemented")
    }

    override fun onShowMediaGalleryClicked(
        mediaId: Long,
    ) {
        TODO("Not yet implemented")
    }

    override fun onShowCompanyProductionClicked() {}
    private fun updateRowSectionToLoading() {
        updateState { screenState ->
            screenState.copy(
                rowSection = TVShowRowSectionUiState.Loading
            )
        }
    }

    override fun onBackClicked() =
        sendNewEffect(TvShowDetailsScreenEffect.NavigateBack)

    override fun onPlayClicked(
        mediaId: Long,
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

    fun toggleTvShowDetailsTab(
        tvShowDetailsTabs: TVShowDetailsTabs,
        tvShowId: Long,
    ) {
        updateState { screenState ->
            if (screenState.tvShowDetailsTabsUiState.tab == tvShowDetailsTabs) return@updateState screenState

            when (tvShowDetailsTabs) {
                TVShowDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(
                    tvShowId = tvShowId,
                )

                TVShowDetailsTabs.REVIEWS -> onShowReviewsClicked(
                    mediaId = tvShowId,
                )

                TVShowDetailsTabs.GALLERY -> onShowMediaGalleryClicked(
                    mediaId = tvShowId,
                )

                TVShowDetailsTabs.COMPANY_PRODUCTION -> onShowCompanyProductionClicked()
                TVShowDetailsTabs.SEASONS -> onSeasonsClicked(
                    tvShowId = tvShowId,
                    numberOfSeasons = _state.value.tvShowUiState.numberOfSeasons
                )
            }
            screenState.copy(
                tvShowDetailsTabsUiState = screenState.tvShowDetailsTabsUiState.copy(
                    tab = tvShowDetailsTabs,
                    isSelected = true
                )
            )
        }
    }

    private companion object {
        val NO_REVIEWS = R.string.there_is_no_reviews
        val NO_GALLERY = R.string.there_is_no_gallery
        val NO_MORE_MEDIA = R.string.there_is_no_more_media
        val NO_COMPANY_PRODUCTION = R.string.there_is_no_company_production
    }
}