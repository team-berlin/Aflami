package com.berlin.aflami.viewmodel.mediadetails

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.ReviewState
import com.berlin.aflami.viewmodel.uistate.ReviewUiState
import com.berlin.aflami.viewmodel.review.toUiState
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.uistate.MediaGalleryUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.SimilarMediaUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.MovieDetailsRepository
import repository.TvShowDetailsRepository
import usecase.GetMovieCastUseCase
import usecase.GetMovieGalleryUseCase
import usecase.GetMovieReviewUseCase
import usecase.GetSeriesCastUseCase
import usecase.GetSeriesReviewUseCase
import usecase.GetSimilarMoviesUseCase
import usecase.GetSimilarSeriesUseCase

class MediaDetailsViewmodel(
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSeriesCastUseCase: GetSeriesCastUseCase,
    private val movieRepo: MovieDetailsRepository,
    private val tvShowRepo: TvShowDetailsRepository,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getSerGalleryUseCase: GetMovieGalleryUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarSeriesUseCase,
    private val movieReviewUseCase: GetMovieReviewUseCase,
    private val seriesReviewUseCase: GetSeriesReviewUseCase
) : ViewModel(), MediaInteractionListener {

    private val _uiState = MutableStateFlow(MediaDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<MediaDetailsScreenEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            getMovieCast(505, MediaType.MOVIE, "ar-EG")
        }
    }

    fun loadMediaDetails(mediaId: Long, mediaType: MediaType, language: String = "en-US") {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val uiState = try {
                when (mediaType) {
                    MediaType.MOVIE -> movieRepo.getMovieDetails(mediaId, language)?.toUiState()
                    MediaType.TV_SHOW -> tvShowRepo.getTvShowDetails(mediaId, language)?.toUiState()
                }
            } catch (e: Exception) {
                _error.value = "Failed to load details: ${e.message}"
                null
            }
            uiState?.let { _uiState.value = it }
            _loading.value = false
        }
    }

    private val _reviewsUiState = MutableStateFlow<ReviewState>(ReviewState.Reviewing.Loading)
    val reviewsUiState = _reviewsUiState.asStateFlow()

    private val _tabSelectedUiState = MutableStateFlow(MovieDetailsTabsUiState())
    val tabSelectedUiState = _tabSelectedUiState.asStateFlow()

    private val _expandedUiStates = mutableStateMapOf<Long, Boolean>()

    fun getReviews(id: Long, mediaType:MediaType) {

        viewModelScope.launch(Dispatchers.IO) {
            _reviewsUiState.update { ReviewState.Reviewing.Loading }

            try {
                val result = when (mediaType) {
                    MediaType.MOVIE -> movieReviewUseCase(id).map { it.toUiState() }
                    MediaType.TV_SHOW -> seriesReviewUseCase(id).map { it.toUiState() }
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


    fun toggleMovieDetailsTab(
        tab: MovieDetailsTabs,
        mediaId: Long,
        mediaType:MediaType
    ) {
        viewModelScope.launch {
            _tabSelectedUiState.update { current ->
                val newSelectedTab = if (current.tab == tab) {
                    MovieDetailsTabs.REVIEWS
                } else {
                    tab
                }

                when (newSelectedTab) {
                    MovieDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(mediaId, mediaType)
                    MovieDetailsTabs.REVIEWS -> getReviews(
                        id = mediaId,
                        mediaType = mediaType
                    )

                    MovieDetailsTabs.GALLERY ->  onShowMediaGalleryClicked(mediaId,mediaType)
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
        _uiState.value = _uiState.value.copy(isPlaying = true)
    }

    override fun onReadMoreDescriptionClicked(id: Long) {
        _uiState.value = _uiState.value.copy(isOverviewExpanded = true)
    }
    override fun onShowCastClicked() {
        viewModelScope.launch {
            _uiEffect.emit(MediaDetailsScreenEffect.NavigateToShowAllCastScreen)
        }
    }

    override fun onRateIconClicked(id: Long) {
        TODO("KNot yet implemented")
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
    private val _galleryMedia = MutableStateFlow<MediaGalleryUiState>(MediaGalleryUiState.Init)
    val galleryMedia: StateFlow<MediaGalleryUiState> = _galleryMedia .asStateFlow()
    override fun onShowMediaGalleryClicked(id: Long,mediaType: MediaType) {
        viewModelScope.launch {
            _galleryMedia.value = MediaGalleryUiState.Loading
            try {
                val mediaGallery = when (mediaType) {
                    MediaType.MOVIE ->  getSerGalleryUseCase(id)
                    MediaType.TV_SHOW ->  getSerGalleryUseCase(id)
                }
                _galleryMedia.value = MediaGalleryUiState.Success(mediaGallery)
            } catch (e: Exception) {
                _galleryMedia.value =
                    MediaGalleryUiState.Error("Failed to load similar media: ${e.message}")
            }
        }
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


    private fun getMovieCast(mediaId: Long, mediaType: MediaType, language: String) {

        viewModelScope.launch {
            val cast = when (mediaType) {
                MediaType.MOVIE -> getMovieCastUseCase(mediaId, language).map { it.toUiState() }
                MediaType.TV_SHOW -> getSeriesCastUseCase(mediaId, language).map { it.toUiState() }
            }
            _uiState.update { newCastState ->
                newCastState.copy(
                    mediaCast = cast,
                    mediaType = mediaType,
                    isLoading = false
                )
            }
        }
    }
}
