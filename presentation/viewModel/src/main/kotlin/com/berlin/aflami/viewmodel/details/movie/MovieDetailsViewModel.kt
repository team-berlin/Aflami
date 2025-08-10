package com.berlin.aflami.viewmodel.details.movie


import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.MediaInteractionListener
import com.berlin.aflami.viewmodel.details.common.MovieDetailsArgs
import com.berlin.aflami.viewmodel.details.common.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.details.common.MoviesTabContent
import com.berlin.aflami.viewmodel.details.common.NO_COMPANY_PRODUCTION
import com.berlin.aflami.viewmodel.details.common.NO_GALLERY
import com.berlin.aflami.viewmodel.details.common.NO_MORE_MEDIA
import com.berlin.aflami.viewmodel.details.common.NO_REVIEWS
import com.berlin.aflami.viewmodel.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.details.common.toggle
import com.berlin.aflami.viewmodel.listFeature.AllFavouriteListsPagingSource
import com.berlin.aflami.viewmodel.mapper.parseRuntime
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.mapper.toReviewUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.toDomain
import com.berlin.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.favouritelist.AddMovieToFavouriteListUseCase
import usecase.favouritelist.GetAllFavouriteListsUseCase
import usecase.mediadetails.GetMovieVideos
import usecase.movie.AddContinueWatchingMovieUseCase
import usecase.movie.GetMovieCastUseCase
import usecase.movie.GetMovieDetailsUseCase
import usecase.movie.GetMovieGalleryUseCase
import usecase.movie.GetMovieReviewUseCase
import usecase.movie.GetSimilarMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val movieReviewUseCase: GetMovieReviewUseCase,
    private val addContinueWatchingMovieUseCase: AddContinueWatchingMovieUseCase,
    private val getMovieVideos: GetMovieVideos,
    private val getIsUserLoggedInUseCase: GetLoginStatus,
    private val getAllFavouriteListsUseCase: GetAllFavouriteListsUseCase,
    private val addMovieToFavouriteListsUseCase: AddMovieToFavouriteListUseCase,
    movieDetailsArgs: MovieDetailsArgs,
) : BaseViewModel<MovieDetailsUiState, MovieDetailsScreenEffect>(
    MovieDetailsUiState()
), MediaInteractionListener {
    private val movieId = movieDetailsArgs.movieId ?: throw IllegalStateException(
        "movie id is null in movie details view model"
    )

    init {
        updateState {
            it.copy(
                movieUiState = it.movieUiState.copy(id = movieId), isScreenLoading = false
            )
        }
        isMovieHasVideo(movieId = movieId)
        getMovieActors(movieId = movieId)
        getMovieDetails(movieId = movieId)
        onShowMoreMediaLikeThisClicked(movieId = movieId)
    }

    private fun isMovieHasVideo(movieId: Long) {
        tryToCall(
            call = {
                getMovieVideos(movieId).videoUrl
            }, onSuccess = { videoUrl ->
                updateState { screenState ->
                    screenState.copy(isMovieHasVideo = true, videoUrl = videoUrl)
                }
            }, onError = ::updateScreenStateToError
        )
    }

    private fun getMovieDetails(movieId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true)
        }
        tryToCall(
            call = {
                val movie = getMovieDetailsUseCase(movieId).toMovieUiState()
                val moviePosters: List<String> = getMovieGalleryUseCase(movieId).posters
                Pair(movie, moviePosters)
            }, onSuccess = { (movieUiState, moviePosters) ->
                updateState { screenState ->
                    screenState.copy(
                        posters = moviePosters, movieUiState = movieUiState, isScreenLoading = false
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
            }, onError = ::updateScreenStateToError
        )
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
                castList = castUiStateList, isScreenLoading = false
            )
        }
    }

    private fun saveMovieToContinueWatching(modelToBeSaved: Movie) {
        viewModelScope.launch {
            addContinueWatchingMovieUseCase(modelToBeSaved)
        }
    }

    override fun onShowMoreMediaLikeThisClicked(
        movieId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(call = {
            getSimilarMoviesUseCase(movieId = movieId).map { tVShow -> tVShow.toMovieUiState() }
        }, onSuccess = ::updateMoreLikeThisSectionWithNewData, onError = {
            updateRowSectionStateToError(
                error = NO_MORE_MEDIA
            )
        })
    }


    private fun updateMoreLikeThisSectionWithNewData(moreLikeThisMovieList: List<MovieUiState>) {
        if (moreLikeThisMovieList.isEmpty()) {
            updateState { screenState ->
                screenState.copy(
                    rowSection = MoviesRowSectionUiState.NoDataFound(
                        UiText.Resource(NO_MORE_MEDIA)
                    ), isScreenLoading = false
                )
            }
        } else {
            updateState {
                it.copy(
                    rowSection = MoviesRowSectionUiState.Success(
                        content = MoviesTabContent.MoreLikeThis(
                            moreMoviesLikeThis = moreLikeThisMovieList
                        )
                    ), isScreenLoading = false
                )
            }
        }
    }

    override fun onShowReviewsClicked(
        movieId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(call = {
            movieReviewUseCase(movieId).map { review -> review.toReviewUiState() }
        }, onSuccess = ::updateReviewRowSectionWithNewData, onError = {
            updateRowSectionStateToError(
                NO_REVIEWS
            )
        })
    }

    private fun updateReviewRowSectionWithNewData(reviewResult: List<ReviewUiState>) {
        if (reviewResult.isEmpty()) {
            updateState { showDetailsUiState ->
                showDetailsUiState.copy(
                    rowSection = MoviesRowSectionUiState.NoDataFound(
                        UiText.Resource(NO_REVIEWS)
                    ), isScreenLoading = false
                )
            }
        } else {
            updateState { screenState ->
                screenState.copy(
                    rowSection = MoviesRowSectionUiState.Success(
                        content = MoviesTabContent.Reviews(
                            movieReviews = reviewResult
                        )
                    ), isScreenLoading = false
                )
            }
        }
    }

    override fun onShowMediaGalleryClicked(
        movieId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(call = {
            getMovieGalleryUseCase(movieId).backdrops
        }, onSuccess = ::updateMediaGellarySectionWithNewImages, onError = {
            updateRowSectionStateToError(
                NO_GALLERY
            )
        })
    }

    private fun updateMediaGellarySectionWithNewImages(backdrops: List<String>) {
        if (backdrops.isEmpty()) {
            updateState {
                it.copy(
                    rowSection = MoviesRowSectionUiState.NoDataFound(
                        UiText.Resource(
                            NO_GALLERY
                        )
                    ), isScreenLoading = false
                )
            }
        } else {
            updateState { screenState ->
                screenState.copy(
                    rowSection = MoviesRowSectionUiState.Success(
                        content = MoviesTabContent.Gallery(
                            images = backdrops
                        )
                    ), isScreenLoading = false
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
                ), isScreenLoading = false
            )
        }
    }

    private fun updateCompanyProductionWithNoDataFound() {
        updateState { screenState ->
            screenState.copy(
                rowSection = MoviesRowSectionUiState.NoDataFound(
                    UiText.Resource(NO_COMPANY_PRODUCTION)
                ), isScreenLoading = false
            )
        }
    }

    override fun onBackClicked() = sendNewEffect(MovieDetailsScreenEffect.NavigateBack)

    override fun onPlayClicked(videoUrl: String) =
        sendNewEffect(MovieDetailsScreenEffect.PlayMedia(videoUrl = videoUrl))

    override fun onAddMovieToFavouriteClicked() {
        checkLoginThen {
            updateState { screenState ->
                screenState.copy(
                    addToListDialog = screenState.addToListDialog.copy(
                        isLoading = true, isAddToListDialogVisible = true
                    )
                )
            }
            tryToCall(call = {
                Pager(
                    config = defaultPageConfigurations(), pagingSourceFactory = {
                        AllFavouriteListsPagingSource(getAllFavouriteListsUseCase)
                    }).flow
            }, onSuccess = {
                updateState { screenState ->
                    screenState.copy(
                        addToListDialog = screenState.addToListDialog.copy(
                            isLoading = false, favouriteLists = it, isAddButtonEnabled = false
                        )
                    )
                }
            }, onError = {
                updateState { screenState ->
                    screenState.copy(
                        addToListDialog = screenState.addToListDialog.copy(
                            isLoading = false, errorMessage = it.message
                        )
                    )
                }
            })
        }
    }

    override fun onReadMoreDescriptionClicked() = updateState { screenState ->
        screenState.copy(
            isDescriptionExpanded = !screenState.isDescriptionExpanded, isScreenLoading = false
        )
    }

    override fun onReadMoreReviewClicked(reviewId: String) = updateState { screenState ->
        screenState.copy(
            expandedReviewIds = screenState.expandedReviewIds.toggle(reviewId),
            isScreenLoading = false
        )
    }

    override fun onShowCastClicked(movieId: Long) = sendNewEffect(
        MovieDetailsScreenEffect.NavigateToShowAllCastScreen(movieId = movieId)
    )

    override fun onMediaCardClicked(movieId: Long) =
        sendNewEffect(MovieDetailsScreenEffect.NavigateToMovieDetailsScreen(movieId))

    override fun onLoginButtonClicked() = sendNewEffect(MovieDetailsScreenEffect.NavigateToLogin)

    override fun onRateIconClicked(movieId: Long) {
        checkLoginThen {
            updateState {
                it.copy(
                    showRatingDialog = true, selectedRatingMediaId = movieId
                )
            }
        }
    }


    override fun onSelectRateClicked(rate: Float) {
        TODO("Not yet implemented")
    }

    override fun onSubmitRateClicked(rate: Int) {
        val mediaId = _state.value.selectedRatingMediaId ?: return
        //TODO: Handle the actual rating submission here, e.g., call usecase.submitRating(mediaId, rating)
        _state.update {
            it.copy(
                showRatingDialog = false, selectedRatingMediaId = null
            )
        }
    }

    override fun onCancelRatingClicked() {
        updateState {
            it.copy(
                showRatingDialog = false, selectedRatingMediaId = null
            )
        }
    }

    override fun onAddMediaToFavouriteListClicked(
        movieId: Long,
        favouriteListId: Int,
    ) {
        tryToCall(
            call = { addMovieToFavouriteListsUseCase(movieId = movieId, listId = favouriteListId) },
            onSuccess = {
                updateState { screenState ->
                    screenState.copy(
                        addToListDialog = screenState.addToListDialog.copy(
                            isLoading = false,
                            errorMessage = null,
                            isAddToListDialogVisible = false,
                            isAddButtonEnabled = false,
                            selectedListId = null
                        )
                    )
                }
                sendNewEffect(MovieDetailsScreenEffect.ShowAddToFavouriteSnackBar(true))
            }, onError = {
                updateState { screenState ->
                    screenState.copy(
                        addToListDialog = screenState.addToListDialog.copy(
                            isLoading = false,
                            errorMessage = null,
                            selectedListId = null,
                            isAddToListDialogVisible = false,
                            isAddButtonEnabled = false
                        )
                    )
                }
                sendNewEffect(MovieDetailsScreenEffect.ShowAddToFavouriteSnackBar(false))
            }

        )

    }

    override fun onSelectFavouriteList(favouriteListId: Int) {
        updateState {
            it.copy(
                addToListDialog = it.addToListDialog.copy(
                    selectedListId = favouriteListId,
                )
            )
        }
    }

    override fun onCreateNewFavouriteListClicked() {
        TODO("Not yet implemented")
    }

    override fun onCancelAddingToFavouriteClicked() =
        updateState { screenState ->
            screenState.copy(
                addToListDialog = screenState.addToListDialog.copy(
                    isLoading = false,
                    errorMessage = null,
                    isAddToListDialogVisible = false,
                    selectedListId = null,
                    favouriteLists = emptyFlow(),
                    isAddButtonEnabled = false
                )
            )
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
                MovieDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(
                    movieId = movieId
                )

                MovieDetailsTabs.REVIEWS -> onShowReviewsClicked(movieId = movieId)
                MovieDetailsTabs.GALLERY -> onShowMediaGalleryClicked(movieId = movieId)
                MovieDetailsTabs.COMPANY_PRODUCTION -> onShowCompanyProductionClicked()
            }
            screenState.copy(
                movieDetailsTabsUiState = screenState.movieDetailsTabsUiState.copy(
                    tab = movieDetailsTabs, isSelected = true
                ), isScreenLoading = false
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
                ), isScreenLoading = false
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
}