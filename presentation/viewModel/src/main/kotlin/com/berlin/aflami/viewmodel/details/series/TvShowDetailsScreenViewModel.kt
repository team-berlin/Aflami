package com.berlin.aflami.viewmodel.details.series

import androidx.compose.ui.text.input.TextFieldValue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import usecase.auth.GetLoginUseCase
import usecase.tvshow.AddContinueWatchingTVShowUseCase
import usecase.tvshow.GetSeasonEpisodesUseCase
import usecase.tvshow.GetSimilarTVShowsUseCase
import usecase.tvshow.GetTVShowCastUseCase
import usecase.tvshow.GetTVShowDetailsUseCase
import usecase.tvshow.GetTVShowGalleryUseCase
import usecase.tvshow.GetTVShowReviewUseCase
import usecase.tvshow.GetTVShowVideos
import usecase.tvshow.RateTvShowUseCase
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsScreenViewModel @Inject constructor(
    private val getTVShowDetailsUseCase: GetTVShowDetailsUseCase,
    private val getTVShowCastUseCase: GetTVShowCastUseCase,
    private val getTVShowGalleryUseCase: GetTVShowGalleryUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarTVShowsUseCase,
    private val getIsUserLoggedInUseCase: GetLoginUseCase,
    private val tvShowReviewUseCase: GetTVShowReviewUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val addContinueWatchingTVShowUseCase: AddContinueWatchingTVShowUseCase,
    private val getTVShowVideos: GetTVShowVideos,
    private val rateTvShowUseCase: RateTvShowUseCase,
    tvShowArgs: TVShowDetailsArgs,
) : BaseViewModel<TVShowDetailsUiState, TvShowDetailsScreenEffect>(TVShowDetailsUiState()),
    TvShowDetailsScreenInteractionListener {


    private val tvShowId = tvShowArgs.tvShowId
        ?: throw IllegalArgumentException("mediaId is null")

    init {
        tvShowId
        loadData()
    }

    private fun loadData(){
        isTVShowHasVideo(tvShowId = tvShowId)
        getTVShowActors(tvShowId = tvShowId)
        getTVShowDetails(tvShowId = tvShowId)
    }

    private fun isTVShowHasVideo(tvShowId: Long) {
        updateState { it.copy(isScreenLoading = true, errorMessage = null) }

        tryToCall(
            call = {
                getTVShowVideos(tvShowId)?.videoUrl
            },
            onSuccess = { videoUrl ->
                updateState {
                    it.copy(
                        isTVShowHasVideo = videoUrl != null,
                        videoUrl = videoUrl.orEmpty()
                    )
                }
            },
            onError = ::updateScreenStateToError
        )
    }


    private fun getTVShowDetails(tvShowId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true, errorMessage = null)
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
                        isScreenLoading = false
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
            screenState.copy(isScreenLoading = true, errorMessage = null)
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
        mediaId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getSimilarTVShowsUseCase(tvShowId = mediaId).map { tVShow -> tVShow.toUiState() }
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
        mediaId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                tvShowReviewUseCase(mediaId).map { review -> review.toReviewUiState() }
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
        mediaId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getTVShowGalleryUseCase(mediaId).backdrops
            },
            onSuccess = ::updateMediaGallerySectionWithNewImages,
            onError = {
                updateRowSectionStateToError(
                    NO_GALLERY
                )
            }
        )
    }

    private fun updateMediaGallerySectionWithNewImages(backdrops: List<String>) {
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

    private fun showSnackBar(message: String, isSuccess: Boolean) {
        updateState { it.copy(snackBarMessage = message, isSnackBarStatusSuccess = isSuccess) }

        viewModelScope.launch {
            delay(3000)
            updateState { it.copy(snackBarMessage = null, isSnackBarStatusSuccess = null) }
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

    override fun onShowCastClicked(mediaId: Long) = sendNewEffect(
        TvShowDetailsScreenEffect.NavigateToShowAllCastScreen(mediaId)
    )

    override fun onMediaCardClicked(mediaId: Long) =
        sendNewEffect(TvShowDetailsScreenEffect.NavigateToMediaDetailsScreen(mediaId))

    override fun onLoginButtonClicked() {
        updateState { it.copy(showLoginDialog = false) }
        sendNewEffect(TvShowDetailsScreenEffect.NavigateToLogin)
    }

    override fun onLoginDialogDismissed() {
        updateState { it.copy(showLoginDialog = false) }
    }

    override fun dismissSnackBar() {
        updateState { it.copy(snackBar = it.snackBar.copy(isVisible = false)) }
    }

    override fun onRateIconClicked(id: Long) {
        checkLoginThen {
            updateState {
                it.copy(
                    showRatingDialog = true,
                    selectedRatingMediaId = id
                )
            }
        }
    }

    override fun onSelectRateClicked(rate: Float) {
        TODO("Not yet implemented")
    }

    override fun onSubmitRateClicked(rate: Int) {
        val tvShowId = _state.value.selectedRatingMediaId?.toInt() ?: return

        viewModelScope.launch {
            tryToCall(
                call = {
                    rateTvShowUseCase(tvShowId, rating = rate.toDouble())
                },
                onSuccess = { result ->
                    updateState {
                        it.copy(
                            showRatingDialog = false,
                            selectedRatingMediaId = null,
                        )
                    }
                    showSnackBar("Successfully submitted rating.",true)
                },
                onError = {
                        stateError ->
                    updateState {
                        it.copy(
                            showRatingDialog = false,
                            selectedRatingMediaId = null,
                            errorMessage = stateError.message
                        )
                    }
                    showSnackBar("Failed to submit rating.",false)
                }
            )
        }
    }

    override fun onCancelRatingClicked() {
        updateState {
            it.copy(
                showRatingDialog = false,
                selectedRatingMediaId = null
            )
        }
    }


    override fun onAddMediaToFavouriteButtomClicked(
        mediaId: Long,
        favouriteListId: Int,
    ) {
        updateState { showDetailsUiState ->
            showDetailsUiState.copy(
                isNotSupportedFeatureDialogVisible = true
            )
        }
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

    override fun onUpdateNewListTitle(newListTitle: TextFieldValue) {
        TODO("Not yet implemented")
    }

    override fun onCreateNewListClicked(listTitle: TextFieldValue) {
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
                TVShowDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(mediaId = tvShowId)
                TVShowDetailsTabs.REVIEWS -> onShowReviewsClicked(mediaId = tvShowId)
                TVShowDetailsTabs.GALLERY -> onShowMediaGalleryClicked(mediaId = tvShowId)
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
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorState.message, isScreenLoading = false
            )
        }
    }

    private fun checkLoginThen(actionIfLoggedIn: () -> Unit) {
        viewModelScope.launch {
            getIsUserLoggedInUseCase().collect { isUserloggedIn ->
                if (isUserloggedIn) actionIfLoggedIn() else updateState { screenState ->
                    screenState.copy(showLoginDialog = true)
                }
            }
        }
    }

    override fun retry() {
        updateState {
            it.copy(errorMessage = null, isScreenLoading = true)
        }
        loadData()
    }

}