package com.berlin.aflami.viewmodel.mediadetails.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabsUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.TabContent
import com.berlin.aflami.viewmodel.mediadetails.uistate.UiText
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.mediadetails.uistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.EpisodesUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.util.toggle
import com.berlin.entity.Episodes
import com.berlin.viewModel.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.mediadetails.AddContinueWatchingMovieUseCase
import usecase.mediadetails.AddContinueWatchingTVShowUseCase
import usecase.mediadetails.GetMovieCastUseCase
import usecase.mediadetails.GetMovieDetailsUseCase
import usecase.mediadetails.GetMovieGalleryUseCase
import usecase.mediadetails.GetMovieReviewUseCase
import usecase.mediadetails.GetSeasonEpisodesUseCase
import usecase.mediadetails.GetSeriesCastUseCase
import usecase.mediadetails.GetSeriesGalleryUseCase
import usecase.mediadetails.GetSeriesReviewUseCase
import usecase.mediadetails.GetSimilarMoviesUseCase
import usecase.mediadetails.GetSimilarSeriesUseCase
import usecase.mediadetails.GetTvShowDetailsUseCase

class MediaDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetSeriesCastUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getSeriesGalleryUseCase: GetSeriesGalleryUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarSeriesUseCase,
    private val movieReviewUseCase: GetMovieReviewUseCase,
    private val seriesReviewUseCase: GetSeriesReviewUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val addContinueWatchingMovieUseCase: AddContinueWatchingMovieUseCase,
    private val addContinueWatchingTVShowUseCase: AddContinueWatchingTVShowUseCase
) : BaseViewModel<MediaDetailsUiState, MediaDetailsScreenEffect>(
    MediaDetailsUiState()
), MediaInteractionListener {

    private companion object {
        const val ID_KEY = "id"
        const val MEDIA_TYPE_KEY = "media_type"
        val NO_REVIEWS = R.string.there_is_no_reviews
        val NO_GALLERY = R.string.there_is_no_gallery
        val NO_MORE_MEDIA = R.string.there_is_no_more_media
        val NO_COMPANY_PRODUCTION = R.string.there_is_no_company_production
    }

    val id: Long = savedStateHandle.get<String>(ID_KEY)?.toLongOrNull() ?: 0L
    val type: MediaType = savedStateHandle.get<String>(MEDIA_TYPE_KEY)
        ?.let { MediaType.valueOf(it) } ?: MediaType.MOVIE

    private val _tabSelectedUiState = MutableStateFlow(MovieDetailsTabsUiState())
    val tabSelectedUiState = _tabSelectedUiState.asStateFlow()

    var companyProductionCache: List<CompanyProductionUiState>? = null

    private val _showLoginRequiredDialog = MutableStateFlow(false)
    val showLoginRequiredDialog = _showLoginRequiredDialog.asStateFlow()

    init {
        if (_state.value.id == 0L && id != 0L) {
            getMediaCast(id, type)
            getMediaDetails(id, type, "en-US")
            onShowReviewsClicked(id, type)
        }
    }


    fun getMediaDetails(mediaId: Long, mediaType: MediaType, language: String) {

        updateState {
            it.copy(isLoading = true, error = null)
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> {
                        val movie = getMovieDetailsUseCase(mediaId, language)
                        companyProductionCache = movie?.productionCompanies?.map { it.toUiState() }
                        movie?.toUiState()
                    }

                    MediaType.TVSHOW -> {
                        val movie = getTvShowDetailsUseCase(mediaId, language)
                        companyProductionCache = movie?.productionCompanies?.map { it.toUiState() }
                        movie?.toUiState()
                    }
                }
            },
            onSuccess = { details ->
                details?.let {
                    updateState {
                        it.copy(
                            id = details.id,
                            title = details.title,
                            overview = details.overview,
                            posterUrl = details.posterUrl,
                            backdropUrl = details.backdropUrl,
                            releaseYear = details.releaseYear,
                            numberOfSeasons = details.numberOfSeasons,
                            rating = details.rating,
                            runtime = details.runtime,
                            genres = details.genres,
                            isLoading = false,
                            mediaType = mediaType,
                            originalCountry = details.originalCountry,
                        )
                    }
                    saveWatchedMedia(mediaType=mediaType)
                }
            },
            onError = { errorState -> handleErrorState(errorState, updateRowSection = true) },
        )
    }

    private fun saveWatchedMedia(mediaType: MediaType){
        viewModelScope.launch {
            when (mediaType) {
                MediaType.MOVIE -> addContinueWatchingMovieUseCase(_state.value.toMovie())
                MediaType.TVSHOW -> addContinueWatchingTVShowUseCase(_state.value.toTVShow())
            }
        }
    }

    fun isDescriptionExpanded(): Boolean {
        return _state.value.isDescriptionExpanded
    }

    fun isReviewExpanded(id: Long): Boolean {
        return _state.value.expandedReviewIds.contains(id)
    }

    fun showLoginDialog(show: Boolean) {
        _showLoginRequiredDialog.value = show
    }

    override fun onBackClicked() {
        sendNewEffect(MediaDetailsScreenEffect.NavigateBack)
    }

    override fun onPlayClicked(id: Long) {
        updateState {
            it.copy(
                isPlaying = true
            )
        }
        sendNewEffect(MediaDetailsScreenEffect.PlayMedia(id = id))
    }

    override fun onReadMoreDescriptionClicked() {
        updateState { state ->
            state.copy(
                isDescriptionExpanded = !state.isDescriptionExpanded
            )
        }
    }

    override fun onReadMoreReviewClicked(id: Long) {
        updateState { state ->
            state.copy(
                expandedReviewIds = state.expandedReviewIds.toggle(id)
            )
        }
    }

    override fun onShowCastClicked() {
        sendNewEffect(
            MediaDetailsScreenEffect.NavigateToShowAllCastScreen(
                mediaId = id,
                mediaType = type
            )
        )
    }

    override fun onRateIconClicked(id: Long) {
        if (true) {
            _showLoginRequiredDialog.value = true
        } else {
            sendNewEffect(MediaDetailsScreenEffect.ShowRatingDialog(id))
        }
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

    override fun onAddMediaToFavouriteListClicked(favouriteListId: Int, mediaId: Int) {
        if (true) {
            _showLoginRequiredDialog.value = true
        } else {
            sendNewEffect(
                MediaDetailsScreenEffect.ShowAddToFavoriteListDialog(
                    favouriteListId = favouriteListId,
                    mediaId = mediaId
                )
            )
        }

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

    override fun onShowMoreMediaLikeThisClicked(mediaId: Long, mediaType: MediaType) {
        updateState {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getSimilarMoviesUseCase(mediaId).map { it.toUIStateMedia() }
                    MediaType.TVSHOW -> getSimilarTVShowsUseCase(mediaId).map { it.toUIStateMedia() }
                }
            },
            onSuccess = { moreLikeMedia ->
                if (moreLikeMedia.isEmpty()) {
                    updateState {
                        it.copy(
                            rowSection = RowSectionUiState.NoDataFound(UiText.Resource(NO_MORE_MEDIA))
                        )
                    }
                } else {
                    updateState {
                        it.copy(
                            rowSection = RowSectionUiState.Success(
                                content = TabContent.MoreLikeThis(
                                    items = moreLikeMedia
                                )
                            )
                        )
                    }
                }

            },
            onError = { errorState -> handleErrorState(errorState, updateRowSection = true) },
        )
    }

    override fun onShowReviewsClicked(mediaId: Long, mediaType: MediaType) {
        updateState {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> movieReviewUseCase(mediaId).map { it.toUiState() }
                    MediaType.TVSHOW -> seriesReviewUseCase(mediaId).map { it.toUiState() }
                }
            },
            onSuccess = { reviewResult ->
                if (reviewResult.isEmpty()) {
                    updateState {
                        it.copy(
                            rowSection = RowSectionUiState.NoDataFound(UiText.Resource(NO_REVIEWS))
                        )
                    }
                } else {
                    updateState {
                        it.copy(
                            rowSection = RowSectionUiState.Success(
                                content = TabContent.Reviews(
                                    items = reviewResult
                                )
                            ),

                            )
                    }
                }
            },
            onError = { errorState -> handleErrorState(errorState, updateRowSection = true) },
        )
    }

    override fun onShowMediaGalleryClicked(id: Long, mediaType: MediaType) {
        updateState {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getMovieGalleryUseCase(id)
                    MediaType.TVSHOW -> getSeriesGalleryUseCase(id)
                }
            },
            onSuccess = { gallery ->
                if (gallery.isEmpty()) {
                    updateState {
                        it.copy(
                            rowSection = RowSectionUiState.NoDataFound(UiText.Resource(NO_GALLERY))
                        )
                    }
                } else {
                    updateState {
                        it.copy(
                            rowSection = RowSectionUiState.Success(
                                content = TabContent.Gallery(
                                    items = gallery
                                )
                            )
                        )
                    }
                }


            },
            onError = { errorState -> handleErrorState(errorState, updateRowSection = true) },
        )
    }

    override fun onShowCompanyProductionClicked() {
        updateState {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        tryToCall(
            call = {},
            onSuccess = {
                if (companyProductionCache?.isEmpty() == true) {
                    updateState { companyProduction ->
                        companyProduction.copy(
                            rowSection = RowSectionUiState.NoDataFound(UiText.Resource(NO_COMPANY_PRODUCTION))
                        )
                    }
                } else {
                    updateState { companyProduction ->
                        companyProduction.copy(
                            rowSection = RowSectionUiState.Success(
                                content = TabContent.CompanyProduction(
                                    items = companyProductionCache ?: emptyList()
                                )
                            )
                        )
                    }
                }
            },
            onError = { errorState -> handleErrorState(errorState, updateRowSection = true) },
        )
    }

    override fun onSeasonsClicked(seriesId: Long, numberOfSeasons: Int) {
        updateState {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        val result: MutableMap<Int, List<Episodes?>> = mutableMapOf()
        tryToCall(
            call = {
                repeat(numberOfSeasons) { seasonNumber ->
                    val episodes: List<Episodes?> = getSeasonEpisodesUseCase(seriesId, seasonNumber)
                    result.put(seasonNumber, episodes)
                }
            },
            onSuccess = { seasons ->
                updateState {
                    it.copy(
                        rowSection = RowSectionUiState.Success(
                            content = TabContent.Season(
                                items = result.mapValues { entry ->
                                    entry.value.mapNotNull { episode ->
                                        episode?.toUiState()
                                    }
                                } as MutableMap<Int, List<EpisodesUiState>>

                            )
                        )
                    )
                }
            },
            onError = { errorState -> handleErrorState(errorState, updateRowSection = true) },
        )
    }

    fun getMediaCast(mediaId: Long, mediaType: MediaType, language: String = "US-EG") {
        updateState {
            it.copy(error = null, isLoading = true)
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getMovieCastUseCase(mediaId, language).map { it.toUiState() }
                    MediaType.TVSHOW -> getSeriesCastUseCase(
                        mediaId,
                        language
                    ).map { it.toUiState() }
                }
            },
            onSuccess = { cast ->
                updateState {
                    it.copy(
                        mediaCast = cast,
                        mediaType = mediaType,
                        isLoading = false
                    )
                }
            },
            onError = { errorState -> handleErrorState(errorState, updateRowSection = true) },
        )
    }

    fun toggleMovieDetailsTab(
        tab: MovieDetailsTabs,
        mediaId: Long,
        mediaType: MediaType,
    ) {
        _tabSelectedUiState.update { current ->
            val newSelectedTab = if (current.tab == tab) {
                MovieDetailsTabs.REVIEWS
            } else {
                tab
            }
            when (newSelectedTab) {
                MovieDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(
                    mediaId = mediaId,
                    mediaType = mediaType
                )

                MovieDetailsTabs.REVIEWS -> onShowReviewsClicked(
                    mediaId = mediaId,
                    mediaType = mediaType
                )

                MovieDetailsTabs.GALLERY -> onShowMediaGalleryClicked(
                    id = mediaId,
                    mediaType = mediaType
                )

                MovieDetailsTabs.COMPANY_PRODUCTION -> onShowCompanyProductionClicked()
                MovieDetailsTabs.SEASON -> onSeasonsClicked(
                    _state.value.id,
                    _state.value.numberOfSeasons ?: 0
                )
            }
            current.copy(
                tab = newSelectedTab,
                isSelected = true
            )
        }
    }

    private fun handleErrorState(errorUiState: ErrorUiState, updateRowSection: Boolean = false) {
        updateState {
            val rowSection = if (updateRowSection) {
                RowSectionUiState.Error(message = errorUiState.message)
            } else it.rowSection
            Log.d("CastViewModel", "getMediaCast: ${errorUiState.message}")
            it.copy(

                error = UiText.Dynamic(errorUiState.message).toString(),
                rowSection = rowSection,
                isLoading = false
            )
        }
    }
}