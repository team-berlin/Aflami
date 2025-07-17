package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import repository.MovieDetailsRepository
import repository.TvShowDetailsRepository

class MediaDetailsViewmodel(
    private val movieRepo: MovieDetailsRepository,
    private val tvShowRepo: TvShowDetailsRepository
) : ViewModel(), MediaInteractionListener {

    private val _uiState = MutableStateFlow(MediaDetailsUiState())
    val uiState: StateFlow<MediaDetailsUiState> = _uiState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadMediaDetails(mediaId: Long, mediaType: MediaType, language: String = "en-US") {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val uiState = try {
                when(mediaType) {
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

}