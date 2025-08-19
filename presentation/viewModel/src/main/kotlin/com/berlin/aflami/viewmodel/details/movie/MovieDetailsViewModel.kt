package com.berlin.aflami.viewmodel.details.movie


import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.MediaDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.details.common.NO_COMPANY_PRODUCTION
import com.berlin.aflami.viewmodel.details.common.NO_GALLERY
import com.berlin.aflami.viewmodel.details.common.NO_MORE_MEDIA
import com.berlin.aflami.viewmodel.details.common.NO_REVIEWS
import com.berlin.aflami.viewmodel.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.details.common.SNACK_BAR_STATUS
import com.berlin.aflami.viewmodel.details.common.SnackBarUiState
import com.berlin.aflami.viewmodel.details.common.toggle
import com.berlin.aflami.viewmodel.list.AllFavouriteListsPagingSource
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
import kotlinx.coroutines.launch
import usecase.auth.GetLoginUseCase
import usecase.favouritelist.AddMovieToFavouriteListUseCase
import usecase.favouritelist.CreateNewFavouriteListUseCase
import usecase.favouritelist.GetAllFavouriteListsUseCase
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
    private val addContinueWatchingMovieUseCase: AddContinueWatchingMovieUseCase,
    private val getMovieVideos: GetMovieVideos,
    private val getIsUserLoggedInUseCase: GetLoginUseCase,
    private val getAllFavouriteListsUseCase: GetAllFavouriteListsUseCase,
    private val addMovieToFavouriteListsUseCase: AddMovieToFavouriteListUseCase,
    private val createNewFavouriteListUseCase: CreateNewFavouriteListUseCase,
    private val rateMovieUseCase: RateMovieUseCase,
    movieDetailsArgs: MovieDetailsArgs,
) : BaseViewModel<MovieDetailsUiState, MovieDetailsScreenEffect>(
    MovieDetailsUiState()
), MediaDetailsScreenInteractionListener {
    private val movieId = movieDetailsArgs.movieId ?: throw IllegalStateException(
        "movie id is null in movie details view model"
    )


    init {
        updateState {
            it.copy(
                movieUiState = it.movieUiState.copy(id = movieId),
            )
        }
      loadData()
    }

    private fun loadData(){
        isMovieHasVideo(movieId = movieId)
        getMovieActors(movieId = movieId)
        getMovieDetails(movieId = movieId)
        onShowMoreMediaLikeThisClicked(mediaId = movieId)
    }
    private fun isMovieHasVideo(movieId: Long) {
        updateState { it.copy(isScreenLoading = true, errorMessage = null) }

        tryToCall(
            call = { getMovieVideos(movieId) },
            onSuccess = { videoUrl ->
                updateState {
                    it.copy(
                        isMovieHasVideo = videoUrl != null,
                        videoUrl = videoUrl.orEmpty()
                    )
                }
            },
            onError = ::updateScreenStateToError
        )
    }


    private fun getMovieDetails(movieId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true, errorMessage = null)
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

//    private fun showSnackBar(message: String, isSuccess: Boolean) {
//        updateState { it.copy(snackBarMessage = message, isSnackBarStatusSuccess = isSuccess) }
//        viewModelScope.launch {
//            delay(3000)
//            updateState { it.copy(snackBarMessage = null, isSnackBarStatusSuccess = null) }
//        }
//    }


    private fun getMovieActors(movieId: Long) {
        updateState { screenState ->
            screenState.copy(isScreenLoading = true, errorMessage = null)
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
        tryToCall(call = {
            getSimilarMoviesUseCase(movieId = mediaId).map { tVShow -> tVShow.toMovieUiState() }
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
                    ),
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
                )
            }
        }
    }

    override fun onShowReviewsClicked(
        mediaId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(call = {
            movieReviewUseCase(mediaId).map { review -> review.toReviewUiState() }
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
                    ),
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
                )
            }
        }
    }

    override fun onShowMediaGalleryClicked(
        mediaId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(call = {
            getMovieGalleryUseCase(mediaId).backdrops
        }, onSuccess = ::updateMovieGallerySectionWithNewImages, onError = {
            updateRowSectionStateToError(
                NO_GALLERY
            )
        })
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
            )
        }
    }

    private fun updateCompanyProductionWithNoDataFound() {
        updateState { screenState ->
            screenState.copy(
                rowSection = MoviesRowSectionUiState.NoDataFound(
                    UiText.Resource(NO_COMPANY_PRODUCTION)
                ),
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
                        isLoading = true, isAddToListDialogVisible = true, errorMessage = null
                    ),
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
                            isLoading = false,
                            favouriteLists = it,
                            isAddButtonEnabled = false,
                            errorMessage = null
                        ),
                    )
                }
            }, onError = {
                updateState { screenState ->
                    screenState.copy(
                        addToListDialog = screenState.addToListDialog.copy(
                            isLoading = false, errorMessage = it.message, isAddButtonEnabled = false
                        ),
                    )
                }
            })
        }
    }

    override fun onReadMoreDescriptionClicked() = updateState { screenState ->
        screenState.copy(
            isDescriptionExpanded = !screenState.isDescriptionExpanded,
        )
    }

    override fun onReadMoreReviewClicked(reviewId: String) = updateState { screenState ->
        screenState.copy(
            expandedReviewIds = screenState.expandedReviewIds.toggle(reviewId),
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

    override fun dismissSnackBar() {
        updateState { it.copy(snackBar = it.snackBar.copy(isVisible = false)) }
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
                            snackBar = SnackBarUiState(
                                isVisible = true,
                                snackBarStatus = SNACK_BAR_STATUS.RATING_ADDED,
                                isOperationSucceeded = true
                            )
                        )
                    }
                    //showSnackBar("Successfully submitted rating.", isSuccess = true)
                },
                onError = { stateError ->
                    updateState {
                        it.copy(
                            showRatingDialog = false,
                            selectedRatingMovieId = null,
                            errorMessage = stateError.message,
                            snackBar = SnackBarUiState(
                                isVisible = true,
                                snackBarStatus = SNACK_BAR_STATUS.RATING_ADDED,
                                isOperationSucceeded = false
                            )

                        )
                    }
                    //showSnackBar("Failed to submit rating.", isSuccess = false)
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

    override fun onAddMediaToFavouriteButtomClicked(
        movieId: Long,
        favouriteListId: Int,
    ) {
        tryToCall(call = {
            addMovieToFavouriteListsUseCase(
                movieId = movieId, listId = favouriteListId
            )
        }, onSuccess = {
            updateState { screenState ->
                screenState.copy(
                    snackBar = SnackBarUiState(
                        isVisible = true,
                        snackBarStatus = SNACK_BAR_STATUS.ADD_MOVIE_TO_LIST,
                        isOperationSucceeded = true
                    ),
                    addToListDialog = screenState.addToListDialog.copy(
                        isLoading = false,
                        errorMessage = null,
                        isAddToListDialogVisible = false,
                        isAddButtonEnabled = false,
                        selectedListId = null
                    ),
                )
            }
        }, onError = { errorUiState ->
            updateState { screenState ->
                screenState.copy(
                    snackBar = SnackBarUiState(
                        isVisible = true,
                        snackBarStatus = SNACK_BAR_STATUS.ADD_MOVIE_TO_LIST,
                        isOperationSucceeded = false,
                        errorUiState = errorUiState
                    ),
                    addToListDialog = screenState.addToListDialog.copy(
                        isLoading = false,
                        errorMessage = null,
                        selectedListId = null,
                        isAddToListDialogVisible = false,
                        isAddButtonEnabled = false
                    ),
                )
            }
        }
        )
    }

    override fun onSelectFavouriteList(favouriteListId: Int) {
        updateState {
            it.copy(
                addToListDialog = it.addToListDialog.copy(
                    selectedListId = favouriteListId,
                ),
            )
        }
    }

    override fun onCreateNewFavouriteListClicked() {
        onCancelAddingToFavouriteClicked()
        updateState { screenState ->
            screenState.copy(
                createNewListDialog = screenState.createNewListDialog.copy(
                    isCreateNewListDialogVisible = true,
                )
            )
        }
    }

    override fun onCancelAddingToFavouriteClicked() = updateState { screenState ->
        screenState.copy(
            addToListDialog = screenState.addToListDialog.copy(
                isLoading = false,
                errorMessage = null,
                isAddToListDialogVisible = false,
                selectedListId = null,
                favouriteLists = emptyFlow(),
                isAddButtonEnabled = false
            ),
        )
    }

    override fun onUpdateNewListTitle(newListTitle: TextFieldValue) {
        updateState { screenState ->
            screenState.copy(
                createNewListDialog = screenState.createNewListDialog.copy(newListTitle = newListTitle)
            )
        }
    }

    override fun onCreateNewListClicked(listTitle: TextFieldValue) {
        tryToCall(call = {
            resetCreateNewListUiState()
            createNewFavouriteListUseCase(listTitle.text)
        }, onSuccess = { createdListId ->
            updateState { screenState ->
                screenState.copy(
                    snackBar = SnackBarUiState(
                        isVisible = true,
                        snackBarStatus = SNACK_BAR_STATUS.CREATE_NEW_LIST,
                        isOperationSucceeded = true
                    )
                )
            }
            tryToCall(call = {
                addMovieToFavouriteListsUseCase(movieId = movieId, listId = createdListId)
            }, onSuccess = {
                updateState { screenState ->
                    screenState.copy(
                        snackBar = SnackBarUiState(
                            isVisible = true,
                            snackBarStatus = SNACK_BAR_STATUS.ADD_MOVIE_TO_LIST,
                            isOperationSucceeded = true
                        )
                    )
                }
            }, onError = {
                updateState { screenState ->
                    screenState.copy(
                        snackBar = SnackBarUiState(
                            isVisible = true,
                            snackBarStatus = SNACK_BAR_STATUS.ADD_MOVIE_TO_LIST,
                            isOperationSucceeded = false
                        )
                    )
                }
            })
        }, onError = {
            updateState { screenState ->
                screenState.copy(
                    snackBar = SnackBarUiState(
                        isVisible = true,
                        snackBarStatus = SNACK_BAR_STATUS.CREATE_NEW_LIST,
                        isOperationSucceeded = false
                    )
                )
            }
        })
    }

    override fun onCancelCreatingNewListClicked() {
        resetCreateNewListUiState()
    }

    private fun resetCreateNewListUiState() {
        updateState { screenState ->
            screenState.copy(
                createNewListDialog = screenState.createNewListDialog.copy(
                    isCreateNewListDialogVisible = false,
                    isCreateNewListButtonEnabled = false,
                    newListTitle = TextFieldValue("")
                )
            )
        }
    }

    fun toggleMovieDetailsTab(
        movieDetailsTabs: MovieDetailsTabs,
        movieId: Long,
    ) {
        updateState { screenState ->
            if (screenState.movieDetailsTabsUiState.tab == movieDetailsTabs) return@updateState screenState

            when (movieDetailsTabs) {
                MovieDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(
                    mediaId = movieId
                )

                MovieDetailsTabs.REVIEWS -> onShowReviewsClicked(mediaId = movieId)
                MovieDetailsTabs.GALLERY -> onShowMediaGalleryClicked(mediaId = movieId)
                MovieDetailsTabs.COMPANY_PRODUCTION -> onShowCompanyProductionClicked()
            }
            screenState.copy(
                movieDetailsTabsUiState = screenState.movieDetailsTabsUiState.copy(
                    tab = movieDetailsTabs, isSelected = true
                ),
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
                rowSection = MoviesRowSectionUiState.NoDataFound(
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