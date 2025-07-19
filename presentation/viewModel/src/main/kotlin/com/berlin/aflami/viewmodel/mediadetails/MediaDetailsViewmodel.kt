package com.berlin.aflami.viewmodel.mediadetails

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.review.toUiState
import com.berlin.aflami.viewmodel.uistate.EpisodesUiState
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.entity.Episodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

class MediaDetailsViewmodel(
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
) : ViewModel(), MediaInteractionListener {


     val id: Long = savedStateHandle.get<String>("id")?.toLongOrNull() ?: 0L
     val type: MediaType = savedStateHandle.get<String>("media_type")
        ?.let { MediaType.valueOf(it) } ?: MediaType.MOVIE


    private val _uiState = MutableStateFlow(MediaDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<MediaDetailsScreenEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _rowSectionUiState = MutableStateFlow<RowSectionUiState>(RowSectionUiState.Loading)
    val rowSectionUiState: StateFlow<RowSectionUiState> = _rowSectionUiState

    private val _tabSelectedUiState = MutableStateFlow(MovieDetailsTabsUiState())
    val tabSelectedUiState = _tabSelectedUiState.asStateFlow()

    private val _expandedUiStates = mutableStateMapOf<Long, Boolean>()

    var companyProductionCache: List<CompanyProductionItem>? = null

    init {
        getReviews(id,type)
    }

   fun getMediaDetails(mediaId: Long, mediaType: MediaType, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _error.value = null
            val uiState = try {
                when (mediaType) {
                    MediaType.MOVIE -> {
                        val result = getMovieDetailsUseCase.invoke(mediaId, language)
                        companyProductionCache =
                            result?.productionCompanies?.map { productCompany ->
                                productCompany.toUiState()
                            }
                        result?.toUiState()
                    }

                    MediaType.TV_SHOW -> {
                        val result = getTvShowDetailsUseCase.invoke(mediaId, language)
                        companyProductionCache =
                            result?.productionCompanies?.map { productCompany ->
                                productCompany.toUiState()
                            }
                        result?.toUiState()
                    }
                }
            } catch (e: Exception) {
                _error.value = "Failed to load details: ${e.message}"
                null
            }

            uiState?.let {
                _uiState.value = _uiState.value.copy(
                    id=it.id,
                    title = it.title,
                    overview = it.overview,
                    posterUrl = it.posterUrl,
                    backdropUrl = it.backdropUrl,
                    releaseYear = it.releaseYear,
                    numberOfSeasons = it.numberOfSeasons,
                    rating = it.rating,
                    runtime = it.runtime,
                    genres = it.genres,
                )
                _loading.value=false

            }


        }
    }

    private fun getReviews(mediaId: Long, mediaType: MediaType) {
        viewModelScope.launch(Dispatchers.IO) {
            _rowSectionUiState.update { RowSectionUiState.Loading }

            try {
                Log.e("id=$id","type=$type")

                val result = when (mediaType) {
                    MediaType.MOVIE -> movieReviewUseCase(mediaId).map { it.toUiState() }
                    MediaType.TV_SHOW -> seriesReviewUseCase(mediaId).map { it.toUiState() }
                }
                Log.e("Review","$result")
                _loading.value=false
                if (result.isEmpty()) {
                    _rowSectionUiState.update { RowSectionUiState.Error("There is no reviews!") }
                } else {
                    _rowSectionUiState.update {
                        RowSectionUiState.Success(
                            content = TabContent.Reviews(
                                items = result
                            )
                        )
                    }
                }

            } catch (error: Exception) {
                _rowSectionUiState.update {
                    RowSectionUiState.Error(
                        error.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private fun getCompanyProduction() {
        _rowSectionUiState.update {
            RowSectionUiState.Success(
                content = TabContent.CompanyProduction(
                    items = companyProductionCache ?: emptyList()
                )
            )
        }
    }

    fun isDescriptionExpanded(id: Long): Boolean {
        return _expandedUiStates[id] ?: false
    }

    override fun onReadMoreReviewClicked(id: Long) {
        _expandedUiStates[id] = !(_expandedUiStates[id] ?: false)
    }

    fun toggleMovieDetailsTab(
        tab: MovieDetailsTabs,
        mediaId: Long,
        mediaType: MediaType,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
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

                    MovieDetailsTabs.REVIEWS -> getReviews(
                        mediaId = mediaId,
                        mediaType = mediaType
                    )

                    MovieDetailsTabs.GALLERY -> onShowMediaGalleryClicked(
                        mediaId = mediaId,
                        mediaType = mediaType
                    )

                    MovieDetailsTabs.COMPANY_PRODUCTION -> getCompanyProduction()
                    MovieDetailsTabs.SEASON -> onSeasonsClicked(
                        _uiState.value.id,
                        _uiState.value.numberOfSeasons!!,
                    ).also {
                        Log.d(
                            "Khairy",
                            "id = ${_uiState.value.id} ,number of seasons = ${_uiState.value.numberOfSeasons}: "
                        )
                    }
                }

                MovieDetailsTabsUiState(
                    tab = newSelectedTab,
                    isSelected = true
                )
            }
        }
    }

    override fun onBackClicked() {
        viewModelScope.launch {
            _uiEffect.emit(MediaDetailsScreenEffect.NavigateBack)
        }
    }

    override fun onPlayClicked(id: Long) {
        _uiState.value = _uiState.value.copy(isPlaying = true)
        viewModelScope.launch {
            _uiEffect.emit(MediaDetailsScreenEffect.PlayMedia(id))
        }
    }

    override fun onReadMoreDescriptionClicked(id: Long) {
        //_uiState.value = _uiState.value.copy(isOverviewExpanded = true)
        _expandedUiStates[id] = !(isDescriptionExpanded(id))
    }

    override fun onShowCastClicked() {
        viewModelScope.launch {
            _uiEffect.emit(MediaDetailsScreenEffect.NavigateToShowAllCastScreen)
        }
    }

    override fun onRateIconClicked(id: Long) {
        viewModelScope.launch {
            _uiEffect.emit(MediaDetailsScreenEffect.ShowRatingSheet(id=id))
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

    override fun onAddMediaToFavouriteListClicked(
        favouriteListId: Int,
        mediaId: Int,
    ) {
        viewModelScope.launch {
            _uiEffect.emit(MediaDetailsScreenEffect
                .ShowAddToFavoriteListSheet( favouriteListId = favouriteListId, mediaId=mediaId))
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
        viewModelScope.launch(Dispatchers.IO) {
            _rowSectionUiState.update { RowSectionUiState.Loading }
            try {
                val result = when (mediaType) {
                    MediaType.MOVIE -> getSimilarMoviesUseCase(mediaId).map { it.toUIStateMedia() }
                    MediaType.TV_SHOW -> getSimilarTVShowsUseCase(mediaId).map { it.toUIStateMedia() }
                }
                if (result.isEmpty()) {
                    _rowSectionUiState.update { RowSectionUiState.Error("There is no more like this!") }
                } else {
                    _rowSectionUiState.update {
                        RowSectionUiState.Success(
                            content = TabContent.MoreLikeThis(
                                items = result
                            )
                        )
                    }
                }
            } catch (error: Exception) {
                _rowSectionUiState.update {
                    RowSectionUiState.Error(
                        error.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    override fun onShowReviewsClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowMediaGalleryClicked(mediaId: Long, mediaType: MediaType) {
        viewModelScope.launch(Dispatchers.IO) {
            _rowSectionUiState.update { RowSectionUiState.Loading }
            try {
                val result = when (mediaType) {
                    MediaType.MOVIE -> getMovieGalleryUseCase(mediaId)
                    MediaType.TV_SHOW -> getSeriesGalleryUseCase(mediaId)
                }
                if (result.isEmpty()) {
                    _rowSectionUiState.update { RowSectionUiState.Error("There is no images!") }
                } else {
                    _rowSectionUiState.update {
                        RowSectionUiState.Success(
                            content = TabContent.Gallery(
                                items = result
                            )
                        )
                    }
                }
            } catch (error: Exception) {
                _rowSectionUiState.update {
                    RowSectionUiState.Error(
                        error.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    override fun onShowCompanyProductionClicked() {

    }

    override fun onSeasonsClicked(seriesId: Long, numberOfSeasons: Int) {
        viewModelScope.launch {
            try {
                _rowSectionUiState.update { RowSectionUiState.Loading }
                val result: MutableMap<Int, List<Episodes?>> = mutableMapOf()
                repeat(numberOfSeasons) { seasonNumber ->
                    val episodes: List<Episodes?> = getSeasonEpisodesUseCase(seriesId, seasonNumber)
                    result.put(seasonNumber, episodes)
                    Log.d(
                        "Khairy",
                        "id = ${_uiState.value.id} ,number of seasons = ${_uiState.value.numberOfSeasons}:$episodes "
                    )
                }
                if (result.isEmpty()) {
                    RowSectionUiState.Error("No seasons found!")
                } else {
                    _rowSectionUiState.update {
                        RowSectionUiState.Success(
                            content = TabContent.Season(
                                items = result.mapValues { entry ->
                                    entry.value.mapNotNull { episode ->
                                        episode?.toUiState()
                                    }
                                } as MutableMap<Int, List<EpisodesUiState>>
                            )
                        )
                    }
                }
            } catch (error: Exception) {
                _rowSectionUiState.update {
                    RowSectionUiState.Error(error.message ?: "Failed to load seasons")
                }
            }
        }
    }

    override fun onShowSeasonEpisodesClicked(tvShowId: Long, seasonId: Long) {
        TODO("Not yet implemented")
    }

    override fun onHideSeasonEpisodesClicked(seasonId: Long) {
        TODO("Not yet implemented")
    }


    fun getMovieCast(mediaId: Long, mediaType: MediaType, language: String) {
        _error.value = null
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val cast = when (mediaType) {
                    MediaType.MOVIE -> getMovieCastUseCase(mediaId, language).map { it.toUiState() }
                    MediaType.TV_SHOW -> getSeriesCastUseCase(
                        mediaId,
                        language
                    ).map {
                        it.toUiState()
                    }

                }
                _loading.value=false
                _uiState.update { newCastState ->
                    newCastState.copy(
                        mediaCast = cast,
                        mediaType = mediaType,
                    )
                }
            }
        } catch (e: Exception) {
            _error.value = "Failed to load all cast: ${e.message}"
            null
        }
    }
}
