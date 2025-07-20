package com.berlin.aflami.viewmodel.mediadetails

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.EpisodesUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaType
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.TabContent
import com.berlin.entity.Episodes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import usecase.GetMovieCastUseCase
import usecase.GetMovieDetailsUseCase
import usecase.GetMovieGalleryUseCase
import usecase.GetMovieReviewUseCase
import usecase.GetSeasonEpisodesUseCase
import usecase.GetSeriesCastUseCase
import usecase.GetSeriesGalleryUseCase
import usecase.GetSeriesReviewUseCase
import usecase.GetSimilarMoviesUseCase
import usecase.GetSimilarSeriesUseCase
import usecase.GetTvShowDetailsUseCase

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
) : BaseViewModel<MediaDetailsUiState, MediaDetailsScreenEffect>(
    MediaDetailsUiState()
), MediaInteractionListener {

    val id: Long = savedStateHandle.get<String>("id")?.toLongOrNull() ?: 0L
    val type: MediaType = savedStateHandle.get<String>("media_type")
        ?.let { MediaType.valueOf(it) } ?: MediaType.MOVIE

    private val _tabSelectedUiState = MutableStateFlow(MovieDetailsTabsUiState())
    val tabSelectedUiState = _tabSelectedUiState.asStateFlow()

    private val _expandedUiStates = mutableStateMapOf<Long, Boolean>()
    var companyProductionCache: List<CompanyProductionUiState>? = null

    init {
        onShowReviewsClicked(id,type)
    }

    fun getMediaDetails(mediaId: Long, mediaType: MediaType, language: String) {

        _state.update {
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

                    MediaType.TV_SHOW -> {
                        val movie = getTvShowDetailsUseCase(mediaId, language)
                        companyProductionCache = movie?.productionCompanies?.map { it.toUiState() }
                        movie?.toUiState()
                    }
                }
            },
            onSuccess = { details ->
                details?.let {
                    _state.update {
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
                            isLoading = false
                        )
                    }
                }
            },
            onError = { throwable ->
                _state.update {
                    it.copy(
                        error = throwable.message,
                        isLoading = false
                    )
                }
            },
        )
    }

    override fun onBackClicked() {
        sendNewEffect(MediaDetailsScreenEffect.NavigateBack)
    }

    override fun onPlayClicked(id: Long) {
        _state.update {
            it.copy(
                isPlaying = true
            )
        }
        sendNewEffect(MediaDetailsScreenEffect.PlayMedia(id = id))
    }

    override fun onReadMoreDescriptionClicked(id: Long) {
        _expandedUiStates[id] = !(_expandedUiStates[id] ?: false)
    }

    override fun onReadMoreReviewClicked(id: Long) {
        _expandedUiStates[id] = !(_expandedUiStates[id] ?: false)
    }

    override fun onShowCastClicked() {
        sendNewEffect(MediaDetailsScreenEffect.NavigateToShowAllCastScreen)
    }

    override fun onRateIconClicked(id: Long) {
        sendNewEffect(MediaDetailsScreenEffect.ShowRatingSheet(id = id))
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
        sendNewEffect(
            MediaDetailsScreenEffect.ShowAddToFavoriteListSheet(
                favouriteListId = favouriteListId,
                mediaId = mediaId
            )
        )
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
        _state.update {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getSimilarMoviesUseCase(mediaId).map { it.toUIStateMedia() }
                    MediaType.TV_SHOW -> getSimilarTVShowsUseCase(mediaId).map { it.toUIStateMedia() }
                }
            },
            onSuccess = { moreLikeMedia ->
                if (moreLikeMedia.isEmpty()) {
                    _state.update {
                        it.copy(
                            rowSection = RowSectionUiState.NoDataFound("There is no more media!")
                        )
                    }
                } else {
                    _state.update {
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
            onError = { throwable ->
                _state.update {
                    it.copy(
                        rowSection = RowSectionUiState.Error(
                            throwable.message ?: "Unknown error"
                        )
                    )
                }
            },
        )
    }

    override fun onShowReviewsClicked(mediaId: Long, mediaType: MediaType) {
        _state.update {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> movieReviewUseCase(mediaId).map { it.toUiState() }
                    MediaType.TV_SHOW -> seriesReviewUseCase(mediaId).map { it.toUiState() }
                }
            },
            onSuccess = { reviewResult ->
                if (reviewResult.isEmpty()) {
                    _state.update {
                        it.copy(
                            rowSection = RowSectionUiState.NoDataFound("There is no reviews!")
                        )
                    }
                } else {
                    _state.update {
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
            onError = { throwable ->
                _state.update {
                    it.copy(
                        rowSection = RowSectionUiState.Error(
                            throwable.message ?: "Unknown error"
                        ),
                    )
                }

            },
        )


    }

    override fun onShowMediaGalleryClicked(mediaId: Long, mediaType: MediaType) {
        _state.update {
            it.copy(
                rowSection = RowSectionUiState.Loading
            )
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getMovieGalleryUseCase(id)
                    MediaType.TV_SHOW -> getSeriesGalleryUseCase(id)
                }

            },
            onSuccess = { gallery ->
                if (gallery.isEmpty()) {
                    _state.update {
                        it.copy(
                            rowSection = RowSectionUiState.NoDataFound("There is no gallery!")
                        )
                    }
                } else {
                    _state.update {
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
            onError = { throwable ->
                _state.update {
                    it.copy(
                        rowSection = RowSectionUiState.Error(
                            throwable.message ?: "Unknown error"
                        )
                    )
                }
            },
        )
    }

    override fun onShowCompanyProductionClicked() {
        _state.update { companyProduction ->
            companyProduction.copy(
                rowSection = RowSectionUiState.Success(
                    content = TabContent.CompanyProduction(
                        items = companyProductionCache ?: emptyList()
                    )
                )
            )
        }
    }

    override fun onSeasonsClicked(seriesId: Long, numberOfSeasons: Int) {
        _state.update {
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
                _state.update {
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
            onError = { throwable ->
                _state.update {
                    it.copy(
                        rowSection = RowSectionUiState.Error(
                            throwable.message ?: "Unknown error"
                        )
                    )
                }
            },
        )
    }

    override fun onShowSeasonEpisodesClicked(tvShowId: Long, seasonId: Long) {
        TODO("Not yet implemented")
    }

    override fun onHideSeasonEpisodesClicked(seasonId: Long) {
        TODO("Not yet implemented")
    }

    fun getMovieCast(mediaId: Long, mediaType: MediaType, language: String) {
        _state.update {
            it.copy(error = null)
        }
        tryToCall(
            call = {
                when (mediaType) {
                    MediaType.MOVIE -> getMovieCastUseCase(mediaId, language).map { it.toUiState() }
                    MediaType.TV_SHOW -> getSeriesCastUseCase(
                        mediaId,
                        language
                    ).map { it.toUiState() }
                }
            },
            onSuccess = { cast ->
                _state.update {
                    it.copy(
                        mediaCast = cast,
                        mediaType = mediaType
                    )
                }
            },
            onError = { throwable ->
                _state.update {
                    it.copy(
                        error = throwable.message,
                    )
                }
            },
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
                    mediaId = mediaId,
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


}