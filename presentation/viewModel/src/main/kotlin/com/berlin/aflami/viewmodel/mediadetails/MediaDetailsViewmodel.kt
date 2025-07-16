package com.berlin.aflami.viewmodel.mediadetails

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.review.ReviewState
import com.berlin.aflami.viewmodel.review.ReviewUiState
import com.berlin.aflami.viewmodel.review.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetMovieReviewUseCase
import usecase.GetSeriesReviewUseCase

class MediaDetailsViewmodel(
    private val movieReviewUseCase: GetMovieReviewUseCase,
    private val seriesReviewUseCase: GetSeriesReviewUseCase
) : ViewModel(), MediaInteractionListener {

    // val args: MediaDetailsArgs = MediaDetailsArgs(savedStateHandle)
    private val _reviewsUiState = MutableStateFlow<ReviewState>(ReviewState.Reviewing.Loading)
    val reviewsUiState = _reviewsUiState.asStateFlow()

    private val _tabSelectedUiState = MutableStateFlow(MovieDetailsTabsUiState())
    val tabSelectedUiState = _tabSelectedUiState.asStateFlow()

    private val _expandedUiStates = mutableStateMapOf<Long, Boolean>()

    fun getReviews(id: Long, mediaType: MediaType) {

        viewModelScope.launch(Dispatchers.IO) {
            _reviewsUiState.update { ReviewState.Reviewing.Loading }

            try {
                val result = when (mediaType) {
                    MediaType.MOVIE -> movieReviewUseCase(id).map { it.toUiState() }
                    MediaType.SERIES -> seriesReviewUseCase(id).map { it.toUiState() }
                }

                if (result.isEmpty()) {
                    _reviewsUiState.update { ReviewState.NoReviewFound }
                } else {
                    onReviewSuccess(result)
                }

            } catch (error: Exception) {
                onReviewError(error.message ?: "Unknown error")
            }
        }
    }

    private fun onReviewSuccess(reviews: List<ReviewUiState>) {
        _reviewsUiState.update {
            ReviewState.Reviewing.Success(reviews)
        }
    }

    private fun onReviewError(error: String) {
        _reviewsUiState.update { ReviewState.Reviewing.Error(error) }
    }

    fun isDescriptionExpanded(id: Long): Boolean {
        return _expandedUiStates[id] ?: false
    }

    override fun onReadMoreDescriptionClicked(id: Long) {
        _expandedUiStates[id] = !(_expandedUiStates[id] ?: false)
    }

    fun toggleMovieDetailsTab(
        tab: MovieDetailsTabs,
        mediaId: Long,
        mediaType: MediaType
    ) {
        viewModelScope.launch {
            _tabSelectedUiState.update { current ->
                val newSelectedTab = if (current.tab == tab) {
                    MovieDetailsTabs.REVIEWS
                } else {
                    tab
                }

                when (newSelectedTab) {
                    MovieDetailsTabs.MORE_LIKE_THIS -> TODO()
                    MovieDetailsTabs.REVIEWS -> getReviews(
                        id = mediaId,
                        mediaType = mediaType
                    )

                    MovieDetailsTabs.GALLERY -> TODO()
                    MovieDetailsTabs.COMPANY_PRODUCTION -> TODO()
                }

                MovieDetailsTabsUiState(
                    tab = newSelectedTab,
                    isSelected = true
                )
            }
        }
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }

    override fun onPlayClicked(id: Long) {
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

    enum class MediaType {
        MOVIE, SERIES
    }
}