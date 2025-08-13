package com.berlin.aflami.viewmodel.details.movie


import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.MediaDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.details.common.NO_COMPANY_PRODUCTION
import com.berlin.aflami.viewmodel.details.common.NO_GALLERY
import com.berlin.aflami.viewmodel.details.common.NO_MORE_MEDIA
import com.berlin.aflami.viewmodel.details.common.NO_REVIEWS
import com.berlin.aflami.viewmodel.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.details.common.toggle
import com.berlin.aflami.viewmodel.mapper.parseRuntime
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.mapper.toReviewUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.toDomain
import com.berlin.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatusUseCase
import usecase.mediadetails.GetMovieVideos
import usecase.movie.AddContinueWatchingMovieUseCase
import usecase.movie.GetMovieCastUseCase
import usecase.movie.GetMovieDetailsUseCase
import usecase.movie.GetMovieGalleryUseCase
import usecase.movie.GetMovieReviewUseCase
import usecase.movie.GetSimilarMoviesUseCase
import usecase.movie.RateMovieUseCase
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val movieReviewUseCase: GetMovieReviewUseCase,
    private val getLoginStatusUseCase: GetLoginStatusUseCase,
    private val addContinueWatchingMovieUseCase: AddContinueWatchingMovieUseCase,
    private val getMovieVideos: GetMovieVideos,
    private val rateMovieUseCase: RateMovieUseCase,
    movieDetailsArgs: MovieDetailsArgs,
) : BaseViewModel<MovieDetailsUiState, MovieDetailsScreenEffect>(
    MovieDetailsUiState()
), MediaDetailsScreenInteractionListener {


    private val movieId = movieDetailsArgs.movieId ?: 0

    init {
        updateState {
            it.copy(
                movieUiState = it.movieUiState.copy(id = movieId),
                isScreenLoading = false
            )
        }
        isMovieHasVideo(movieId = movieId)
        getMovieActors(movieId = movieId)
        getMovieDetails(movieId = movieId)
        onShowMoreMediaLikeThisClicked(mediaId = movieId)
    }

    private fun isMovieHasVideo(movieId: Long) {
        tryToCall(
            call = {
                getMovieVideos(movieId).videoUrl
            },
            onSuccess = { videoUrl ->
                updateState { screenState ->
                    screenState.copy(isMovieHasVideo = true, videoUrl = videoUrl)
                }
            },
            onError = ::updateScreenStateToError
        )
    }

    private fun getMovieDetails(movieId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true)
        }
        tryToCall(
            call = {
                val movie =
                    getMovieDetailsUseCase(movieId).toMovieUiState()
                val moviePosters: List<String> = getMovieGalleryUseCase(movieId).posters
                Pair(movie, moviePosters)
            },
            onSuccess = { (movieUiState, moviePosters) ->
                updateState { screenState ->
                    screenState.copy(
                        posters = moviePosters,
                        movieUiState = movieUiState,
                        isScreenLoading = false
                    )
                }
                saveMovieToContinueWatching(
                    Movie(
                        id = movieId,
                        rating = movieUiState.rating.toDouble(),
                        title = movieUiState.title,
                        releaseDate = movieUiState.releaseDate,
                        posterURL = movieUiState.posterUrl,
                        screenShot = movieUiState.posterUrl,
                        description = movieUiState.description,
                        genres = movieUiState.genre.map { it.toDomain() },
                        duration = movieUiState.duration.parseRuntime(),
                        hasVideo = true,
                        companyProductions = emptyList(),
                        originCountry = "",
                        galleryUrl = emptyList(),
                        reviews = emptyList(),
                        isFavourite = false
                    )
                )
            },
            onError = ::updateScreenStateToError
        )
    }

    private fun showSnackBar(message: String, isSuccess: Boolean) {
        updateState { it.copy(snackBarMessage = message, isSnackBarStatusSuccess = isSuccess) }
        viewModelScope.launch {
            delay(3000)
            updateState { it.copy(snackBarMessage = null, isSnackBarStatusSuccess = null) }
        }
    }


    private fun getMovieActors(movieId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true)
        }
        tryToCall(
            call = {
                getMovieCastUseCase(movieId).map { it.toActorUiState() }
            },
            onSuccess = ::updateScreenWithNewActors,
            onError = ::updateScreenStateToError,
        )
    }

    private fun updateScreenWithNewActors(castUiStateList: List<ActorUiState>) {
        updateState { screenState ->
            screenState.copy(
                castList = castUiStateList,
                isScreenLoading = false
            )
        }
    }

    private fun saveMovieToContinueWatching(modelToBeSaved: Movie) {
        viewModelScope.launch {
            addContinueWatchingMovieUseCase(modelToBeSaved)
        }
    }

    override fun onShowMoreMediaLikeThisClicked(
        mediaId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getSimilarMoviesUseCase(movieId = mediaId).map { tVShow -> tVShow.toMovieUiState() }
            },
            onSuccess = ::updateMoreLikeThisSectionWithNewData,
            onError = {
                updateRowSectionStateToError(
                    error = NO_MORE_MEDIA
                )
            }
        )
    }


    private fun updateMoreLikeThisSectionWithNewData(moreLikeThisMovieList: List<MovieUiState>) {
        if (moreLikeThisMovieList.isEmpty()) {
            updateState { screenState ->
                screenState.copy(
                    rowSection = MoviesRowSectionUiState.NoDataFound(
                        UiText.Resource(NO_MORE_MEDIA)
                    ),
                    isScreenLoading = false
                )
            }
        } else {
            updateState {
                it.copy(
                    rowSection = MoviesRowSectionUiState.Success(
                        content = MoviesTabContent.MoreLikeThis(
                            moreMoviesLikeThis = moreLikeThisMovieList
                        )
                    ),
                    isScreenLoading = false
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
                movieReviewUseCase(mediaId).map { review -> review.toReviewUiState() }
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
                    rowSection = MoviesRowSectionUiState.NoDataFound(
                        UiText.Resource(NO_REVIEWS)
                    ),
                    isScreenLoading = false
                )
            }
        } else {
            updateState { screenState ->
                screenState.copy(
                    rowSection = MoviesRowSectionUiState.Success(
                        content = MoviesTabContent.Reviews(
                            movieReviews = reviewResult
                        )
                    ),
                    isScreenLoading = false
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
                getMovieGalleryUseCase(mediaId).backdrops
            },
            onSuccess = ::updateMovieGallerySectionWithNewImages,
            onError = {
                updateRowSectionStateToError(
                    NO_GALLERY
                )
            }
        )
    }

    private fun updateMovieGallerySectionWithNewImages(backdrops: List<String>) {
        if (backdrops.isEmpty()) {
            updateState {
                it.copy(
                    rowSection = MoviesRowSectionUiState.NoDataFound(
                        UiText.Resource(
                            NO_GALLERY
                        )
                    ),
                    isScreenLoading = false
                )
            }
        } else {
            updateState { screenState ->
                screenState.copy(
                    rowSection = MoviesRowSectionUiState.Success(
                        content = MoviesTabContent.Gallery(
                            images = backdrops
                        )
                    ),
                    isScreenLoading = false
                )
            }
        }
    }

    override fun onShowCompanyProductionClicked() {
        updateRowSectionToLoading()
        val companyProductionUiState = state.value.movieUiState.companyProductionUiState
        if (companyProductionUiState.isEmpty()) {
            updateCompanyProductionWithNoDataFound()
        } else {
            updateCompanyProductionSectionWithNewData(companyProductionUiState)
        }
    }

    private fun updateCompanyProductionSectionWithNewData(companyProductionUiState: List<CompanyProductionUiState>) {
        updateState { companyProduction ->
            companyProduction.copy(
                rowSection = MoviesRowSectionUiState.Success(
                    content = MoviesTabContent.CompanyProduction(
                        companyProductionsList = companyProductionUiState
                    )
                ),
                isScreenLoading = false
            )
        }
    }

    private fun updateCompanyProductionWithNoDataFound() {
        updateState { screenState ->
            screenState.copy(
                rowSection = MoviesRowSectionUiState.NoDataFound(
                    UiText.Resource(NO_COMPANY_PRODUCTION)
                ),
                isScreenLoading = false
            )
        }
    }

    override fun onBackClicked() = sendNewEffect(MovieDetailsScreenEffect.NavigateBack)

    override fun onPlayClicked(videoUrl: String) =
        sendNewEffect(MovieDetailsScreenEffect.PlayMedia(videoUrl = videoUrl))

    override fun onReadMoreDescriptionClicked() = updateState { screenState ->
        screenState.copy(
            isDescriptionExpanded = !screenState.isDescriptionExpanded,
            isScreenLoading = false
        )
    }

    override fun onReadMoreReviewClicked(reviewId: String) = updateState { screenState ->
        screenState.copy(
            expandedReviewIds = screenState.expandedReviewIds.toggle(reviewId),
            isScreenLoading = false
        )
    }

    override fun onShowCastClicked(mediaId: Long) = sendNewEffect(
        MovieDetailsScreenEffect.NavigateToShowAllCastScreen(movieId = mediaId)
    )

    override fun onMediaCardClicked(mediaId: Long) =
        sendNewEffect(MovieDetailsScreenEffect.NavigateToMovieDetailsScreen(mediaId))

    override fun onLoginButtonClicked() {
        updateState { it.copy(showLoginDialog = false) }
        sendNewEffect(MovieDetailsScreenEffect.NavigateToLogin)

    }

    override fun onLoginDialogDismissed() {
        updateState { it.copy(showLoginDialog = false) }
    }

    override fun onRateIconClicked(id: Long) {
        checkLoginThen {
            updateState {
                it.copy(
                    showRatingDialog = true,
                    selectedRatingMovieId = id
                )
            }
        }
    }

    override fun onSelectRateClicked(rate: Float) {
        TODO("Not yet implemented")
    }

    override fun onSubmitRateClicked(rate: Int) {
        val movieId = _state.value.selectedRatingMovieId?.toInt() ?: return

        viewModelScope.launch {
            tryToCall(
                call = {
                    rateMovieUseCase(movieId, rating = rate.toDouble())
                },
                onSuccess = { result ->
                    updateState {
                        it.copy(
                            showRatingDialog = false,
                            selectedRatingMovieId = null,
                        )
                    }
                    showSnackBar("Successfully submitted rating.", isSuccess = true)
                },
                onError = {
                    stateError ->
                    updateState {
                        it.copy(
                            showRatingDialog = false,
                            selectedRatingMovieId = null,
                            errorMessage = stateError.message
                        )
                    }
                    showSnackBar("Failed to submit rating.", isSuccess = false)
                }
            )
        }
    }

    override fun onCancelRatingClicked() {
        updateState {
            it.copy(
                showRatingDialog = false,
                selectedRatingMovieId = null
            )
        }
    }

    override fun onAddMediaToFavouriteListClicked(
        favouriteListId: Int,
        mediaId: Long,
    ) {
        checkLoginThen {
            updateState {
                it.copy(
                    showAddToListDialog = true,
                    selectedFavouriteListId = favouriteListId,
                    selectedAddToListMovieId = mediaId
                )
            }
        }
    }

    override fun onSelectFavouriteList(favouriteListId: Int) {
        updateState {
            it.copy(
                showAddToListDialog = false,
                selectedAddToListMovieId = null,
                selectedFavouriteListId = null
            )
        }
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

    fun toggleMovieDetailsTab(
        movieDetailsTabs: MovieDetailsTabs,
        movieId: Long,
    ) {
        updateState { screenState ->
            if (screenState.movieDetailsTabsUiState.tab == movieDetailsTabs) return@updateState screenState

            when (movieDetailsTabs) {
                MovieDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(mediaId = movieId)
                MovieDetailsTabs.REVIEWS -> onShowReviewsClicked(mediaId = movieId)
                MovieDetailsTabs.GALLERY -> onShowMediaGalleryClicked(mediaId = movieId)
                MovieDetailsTabs.COMPANY_PRODUCTION -> onShowCompanyProductionClicked()
            }
            screenState.copy(
                movieDetailsTabsUiState = screenState.movieDetailsTabsUiState.copy(
                    tab = movieDetailsTabs, isSelected = true
                ),
                isScreenLoading = false
            )
        }
    }

    private fun updateRowSectionToLoading() {
        updateState { screenState ->
            screenState.copy(
                rowSection = MoviesRowSectionUiState.Loading,
            )
        }
    }

    private fun updateRowSectionStateToError(error: Int) {
        updateState { screenState ->
            screenState.copy(
                rowSection = MoviesRowSectionUiState.NoDataFound(
                    UiText.Resource(error)
                ),
                isScreenLoading = false
            )
        }
    }

    private fun updateScreenStateToError(errorState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorState.message,
                isScreenLoading = false
            )
        }
    }

    private fun checkLoginThen(actionIfLoggedIn: () -> Unit) {
        viewModelScope.launch {
            if (getLoginStatusUseCase()) {
                actionIfLoggedIn()
            } else {
                updateState { it.copy(showLoginDialog = true) }
            }
        }
    }
}