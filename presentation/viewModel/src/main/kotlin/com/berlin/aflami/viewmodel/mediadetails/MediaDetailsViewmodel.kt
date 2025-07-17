package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
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
import usecase.GetSeriesCastUseCase
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
    private val getSimilarTVShowsUseCase: GetSimilarSeriesUseCase
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

    override fun onShowMediaGalleryClicked(id: Long) {
        viewModelScope.launch {
            getSerGalleryUseCase(505)
            getMovieGalleryUseCase(505)
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
