package com.berlin.aflami.viewmodel.details.series

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.NO_COMPANY_PRODUCTION
import com.berlin.aflami.viewmodel.details.common.NO_GALLERY
import com.berlin.aflami.viewmodel.details.common.NO_MORE_MEDIA
import com.berlin.aflami.viewmodel.details.common.NO_REVIEWS
import com.berlin.aflami.viewmodel.details.common.NO_SEASON
import com.berlin.aflami.viewmodel.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.details.common.TVShowDetailsArgs
import com.berlin.aflami.viewmodel.details.common.toggle
import com.berlin.aflami.viewmodel.details.movie.UiText
import com.berlin.aflami.viewmodel.mapper.parseRuntime
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.mapper.toEpisodeUiState
import com.berlin.aflami.viewmodel.mapper.toReviewUiState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.aflami.viewmodel.shareduistate.toDomain
import com.berlin.entity.TVShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.tvshow.AddContinueWatchingTVShowUseCase
import usecase.tvshow.GetSeasonEpisodesUseCase
import usecase.tvshow.GetSimilarTVShowsUseCase
import usecase.tvshow.GetTVShowCastUseCase
import usecase.tvshow.GetTVShowDetailsUseCase
import usecase.tvshow.GetTVShowGalleryUseCase
import usecase.tvshow.GetTVShowReviewUseCase
import usecase.tvshow.GetTVShowVideos
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsScreenViewModel @Inject constructor(
    private val getTVShowDetailsUseCase: GetTVShowDetailsUseCase,
    private val getTVShowCastUseCase: GetTVShowCastUseCase,
    private val getTVShowGalleryUseCase: GetTVShowGalleryUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarTVShowsUseCase,
    private val tvShowReviewUseCase: GetTVShowReviewUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val addContinueWatchingTVShowUseCase: AddContinueWatchingTVShowUseCase,
    private val getTVShowVideos: GetTVShowVideos,
    tvShowArgs: TVShowDetailsArgs,
) : BaseViewModel<TVShowDetailsUiState, TvShowDetailsScreenEffect>(TVShowDetailsUiState()),
    TvShowDetailsScreenInteractionListener {

    private val _showLoginRequiredDialog = MutableStateFlow(false)
    val showLoginRequiredDialog = _showLoginRequiredDialog.asStateFlow()


    private val tvShowId = tvShowArgs.tvShowId
        ?: throw IllegalArgumentException("mediaId is null")

    init {
        tvShowId
        isTVShowHasVideo(tvShowId = tvShowId)
        getTVShowActors(tvShowId = tvShowId)
        getTVShowDetails(tvShowId = tvShowId)
    }

    private fun isTVShowHasVideo(tvShowId: Long) {
        tryToCall(
            call = {
                getTVShowVideos(tvShowId).videoUrl
            },
            onSuccess = { videoUrl ->
                updateState { screenState ->
                    screenState.copy(isTVShowHasVideo = true, videoUrl = videoUrl)
                }
            },
            onError = ::updateScreenStateToError
        )
    }

    private fun getTVShowDetails(tvShowId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true)
        }
        tryToCall(
            call = {
                val tvShow =
                    getTVShowDetailsUseCase(tvShowId).toUiState()
                val tvShowPosters: List<String> = getTVShowGalleryUseCase(tvShowId).posters
                Pair(tvShow, tvShowPosters)
            },
            onSuccess = { (tvShowUiState, tvShowPosters) ->
                updateState { screenState ->
                    screenState.copy(
                        posters = tvShowPosters,
                        tvShowUiState = tvShowUiState,
                    )
                }
                onSeasonsClicked(
                    tvShowId = tvShowId,
                    numberOfSeasons = tvShowUiState.numberOfSeasons
                )

                saveTVShowToContinueWatching(
                    TVShow(
                        id = tvShowId,
                        rating = tvShowUiState.rating.toDouble(),
                        title = tvShowUiState.title,
                        releaseDate = tvShowUiState.releaseDate,
                        posterURL = tvShowUiState.posterUrl,
                        screenShot = tvShowUiState.posterUrl,
                        description = tvShowUiState.description,
                        genres = tvShowUiState.genre.map { it.toDomain() },
                        duration = tvShowUiState.duration.parseRuntime(),
                        hasVideo = tvShowUiState.hasVideo,
                        companyProductions = emptyList(),
                        originCountry = tvShowUiState.originCountry,
                        galleryUrl = emptyList(),
                        numberOfSeasons = 0,
                    )
                )
            },
            onError = ::updateScreenStateToError
        )
    }

    private fun getTVShowActors(tvShowId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true)
        }
        tryToCall(
            call = {
                getTVShowCastUseCase(tvShowId).map { it.toActorUiState() }
            },
            onSuccess = ::updateScreenWithNewActors,
            onError = ::updateScreenStateToError,
        )
    }

    private fun updateScreenWithNewActors(castUiStateList: List<ActorUiState>) {
        updateState { screenState ->
            screenState.copy(
                castList = castUiStateList,
            )
        }
    }

    private fun saveTVShowToContinueWatching(modelToBeSaved: TVShow) {
        viewModelScope.launch {
            addContinueWatchingTVShowUseCase(modelToBeSaved)
        }
    }

    override fun onSeasonsClicked(tvShowId: Long, numberOfSeasons: Int) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                fetchSeasonToEpisodesMap(tvShowId, numberOfSeasons)
            },
            onSuccess = ::updateRowSectionWithNewSeasonToEpisodesMap,
            onError = {
                updateRowSectionStateToError(
                    NO_SEASON
                )
            }
        )
    }

    private suspend fun fetchSeasonToEpisodesMap(
        tvShowId: Long,
        numberOfSeasons: Int,
    ): MutableMap<Int, List<EpisodeUiState>> {
        val seasonToEpisodesMap: MutableMap<Int, List<EpisodeUiState>> = mutableMapOf()
        repeat(numberOfSeasons) { seasonNumber ->
            val episodes: List<EpisodeUiState> = getSeasonEpisodesUseCase(
                tvShowId, seasonNumber
            ).map { episode -> episode.toEpisodeUiState() }
            seasonToEpisodesMap.put(seasonNumber, episodes)
        }
        return seasonToEpisodesMap
    }

    private fun updateRowSectionWithNewSeasonToEpisodesMap(seasons: MutableMap<Int, List<EpisodeUiState>>) {
        updateState { screenState ->
            screenState.copy(
                rowSection = TVShowRowSectionUiState.Success(
                    content = TVShowTabContent.Season(
                        seasonToEpisodesMap = seasons
                    )
                ),
            )
        }
    }

    override fun onShowMoreMediaLikeThisClicked(
        tvShowId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getSimilarTVShowsUseCase(tvShowId = tvShowId).map { tVShow -> tVShow.toUiState() }
            },
            onSuccess = ::updateMoreLikeThisSectionWithNewData,
            onError = {
                updateRowSectionStateToError(
                    error = NO_MORE_MEDIA
                )
            }
        )
    }


    private fun updateMoreLikeThisSectionWithNewData(moreLikeThisTVShowList: List<TVShowUiState>) {
        if (moreLikeThisTVShowList.isEmpty()) {
            updateState { screenState ->
                screenState.copy(
                    rowSection = TVShowRowSectionUiState.NoDataFound(
                        UiText.Resource(NO_MORE_MEDIA)
                    )
                )
            }
        } else {
            updateState {
                it.copy(
                    rowSection = TVShowRowSectionUiState.Success(
                        content = TVShowTabContent.MoreLikeThis(
                            items = moreLikeThisTVShowList
                        )
                    ),
                )
            }
        }
    }

    override fun onShowReviewsClicked(
        tvShowId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                tvShowReviewUseCase(tvShowId).map { review -> review.toReviewUiState() }
            },
            onSuccess = ::updateReviewRowSectionWithNewData,
            onError = {
                updateRowSectionStateToError(
                    NO_REVIEWS
                )
            }
        )
    }

    private fun updateReviewRowSectionWithNewData(reviewResult: List<ReviewUiState>) {
        if (reviewResult.isEmpty()) {
            updateState { showDetailsUiState ->
                showDetailsUiState.copy(
                    rowSection = TVShowRowSectionUiState.NoDataFound(
                        UiText.Resource(NO_REVIEWS)
                    )
                )
            }
        } else {
            updateState { screenState ->
                screenState.copy(
                    rowSection = TVShowRowSectionUiState.Success(
                        content = TVShowTabContent.Reviews(
                            reviews = reviewResult
                        )
                    )
                )
            }
        }
    }

    override fun onShowMediaGalleryClicked(
        tvShowId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getTVShowGalleryUseCase(tvShowId).backdrops
            },
            onSuccess = ::updateMediaGellarySectionWithNewImages,
            onError = {
                updateRowSectionStateToError(
                    NO_GALLERY
                )
            }
        )
    }

    private fun updateMediaGellarySectionWithNewImages(backdrops: List<String>) {
        if (backdrops.isEmpty()) {
            updateState {
                it.copy(
                    rowSection = TVShowRowSectionUiState.NoDataFound(
                        UiText.Resource(
                            NO_GALLERY
                        )
                    )
                )
            }
        } else {
            updateState { screenState ->
                screenState.copy(
                    rowSection = TVShowRowSectionUiState.Success(
                        content = TVShowTabContent.Gallery(
                            images = backdrops
                        )
                    )
                )
            }
        }
    }

    override fun onShowCompanyProductionClicked() {
        updateRowSectionToLoading()
        val companyProductionUiState = state.value.tvShowUiState.companyProductionUiState
        if (companyProductionUiState.isEmpty()) {
            updateCompanyProductionWithNoDataFound()
        } else {
            updateCompanyProductionSectionWithNewData(companyProductionUiState)
        }
    }

    private fun updateCompanyProductionSectionWithNewData(companyProductionUiState: List<CompanyProductionUiState>) {
        updateState { companyProduction ->
            companyProduction.copy(
                rowSection = TVShowRowSectionUiState.Success(
                    content = TVShowTabContent.CompanyProduction(
                        companyProductionStates = companyProductionUiState
                    )
                )
            )
        }
    }

    private fun updateCompanyProductionWithNoDataFound() {
        updateState { screenState ->
            screenState.copy(
                rowSection = TVShowRowSectionUiState.NoDataFound(
                    UiText.Resource(NO_COMPANY_PRODUCTION)
                )
            )
        }
    }

    override fun onBackClicked() = sendNewEffect(TvShowDetailsScreenEffect.NavigateBack)

    override fun onPlayClicked(videoUrl: String) =
        sendNewEffect(TvShowDetailsScreenEffect.PlayMedia(videoUrl = videoUrl))

    override fun onAddMovieToFavouriteClicked() {
        updateState { showDetailsUiState ->
            showDetailsUiState.copy(
                isNotSupportedFeatureDialogVisible = true
            )
        }
    }

    override fun onReadMoreDescriptionClicked() = updateState { screenState ->
        screenState.copy(
            isDescriptionExpanded = !screenState.isDescriptionExpanded
        )
    }

    override fun onReadMoreReviewClicked(reviewId: String) = updateState { screenState ->
        screenState.copy(
            expandedReviewIds = screenState.expandedReviewIds.toggle(reviewId)
        )
    }

    override fun onShowCastClicked(tvShowId: Long) = sendNewEffect(
        TvShowDetailsScreenEffect.NavigateToShowAllCastScreen(tvShowId)
    )

    override fun onMediaCardClicked(tvShowId: Long) =
        sendNewEffect(TvShowDetailsScreenEffect.NavigateToMediaDetailsScreen(tvShowId))

    override fun onLoginButtonClicked() {
        _showLoginRequiredDialog.value = false
        sendNewEffect(TvShowDetailsScreenEffect.NavigateToLogin)
    }


    override fun onRateIconClicked(tvShowId: Long) {
        checkLoginThen {
            updateState {
                it.copy(
                    showRatingDialog = true,
                    selectedRatingMediaId = tvShowId,
                )
            }
        }
    }
//        sendNewEffect(TvShowDetailsScreenEffect.ShowRatingDialog(tvShowId))


    override fun onSelectRateClicked(rate: Float) {
        TODO("Not yet implemented")
    }

    override fun onSubmitRateClicked(rate: Int) {
        val mediaId = _state.value.selectedRatingMediaId ?: return
        //TODO: Handle the actual rating submission here, e.g., call usecase.submitRating(mediaId, rating)
        _state.update {
            it.copy()
        }
    }

    override fun onCancelRatingClicked() {
        updateState {
            it.copy()
        }
    }


    override fun onAddMediaToFavouriteListClicked(
        mediaId: Long,
        favouriteListId: Int,
    ) {
        TODO("Not yet implemented")
    }

    override fun onSelectFavouriteList(favouriteListId: Int) {
        updateState {
            it.copy()
        }
    }

    override fun onCreateNewFavouriteListClicked() {
        TODO("Not yet implemented")
    }

    override fun onCancelAddingToFavouriteClicked() {
        updateState { screenState -> screenState.copy(isNotSupportedFeatureDialogVisible = false) }
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
                TVShowDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(tvShowId = tvShowId)
                TVShowDetailsTabs.REVIEWS -> onShowReviewsClicked(tvShowId = tvShowId)
                TVShowDetailsTabs.GALLERY -> onShowMediaGalleryClicked(tvShowId = tvShowId)
                TVShowDetailsTabs.COMPANY_PRODUCTION -> onShowCompanyProductionClicked()
                TVShowDetailsTabs.SEASONS -> onSeasonsClicked(
                    tvShowId = tvShowId,
                    numberOfSeasons = _state.value.tvShowUiState.numberOfSeasons
                )
            }
            screenState.copy(
                tvShowDetailsTabsUiState = screenState.tvShowDetailsTabsUiState.copy(
                    tab = tvShowDetailsTabs, isSelected = true
                )
            )
        }
    }

    private fun updateRowSectionToLoading() {
        updateState { screenState ->
            screenState.copy()
        }
    }

    private fun updateRowSectionStateToError(error: Int) {
        updateState { screenState ->
            screenState.copy(
                rowSection = TVShowRowSectionUiState.NoDataFound(
                    UiText.Resource(error)
                ),
            )
        }
    }

    private fun updateScreenStateToError(errorState: ErrorUiState) {
        Log.e("WOWTEST", "Error: ${errorState.message}")
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorState.message
            )
        }
    }

    private fun checkLoginThen(actionIfLoggedIn: () -> Unit) {
        viewModelScope.launch {
            // handle is logged in or not
            if (true) {
                actionIfLoggedIn()
            } else {
                _showLoginRequiredDialog.value = true
            }
        }
    }
}