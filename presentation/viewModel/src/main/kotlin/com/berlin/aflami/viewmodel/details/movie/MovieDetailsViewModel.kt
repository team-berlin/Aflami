package com.berlin.aflami.viewmodel.details.movie


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.MovieDetailsArgs
import com.berlin.aflami.viewmodel.details.common.MediaInteractionListener
import com.berlin.aflami.viewmodel.details.common.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.details.common.MoviesTabContent
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
import com.berlin.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    movieDetailsArgs: MovieDetailsArgs,
) : BaseViewModel<MovieDetailsScreenState, MovieDetailsScreenEffect>(
    MovieDetailsScreenState()
), MediaInteractionListener {

    private val movieId = movieDetailsArgs.movieId ?: 0

    init {
        Log.d("MOVIEDETAILS","${movieDetailsArgs.movieId}")
        updateState {
            it.copy(
                movieUiState = it.movieUiState.copy(id = movieId),
                isScreenLoading = false
            )
        }
        Log.d("MOVIEDETAILS", "movieId: ${_state.value.movieUiState.id}")
        isMovieHasVideo(movieId = movieId)
        getMovieActors(movieId = movieId)
        getMovieDetails(movieId = movieId)
        onShowMoreMediaLikeThisClicked(movieId = movieId)
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
                        description =movieUiState.description,
                        genres = emptyList(),
                        duration = movieUiState.duration.parseRuntime(),
                        hasVideo = true,
                        companyProductions = emptyList(),
                        originCountry = "",
                        galleryUrl = emptyList(),
                        reviews = emptyList(),
                    )
                )
            },
            onError = ::updateScreenStateToError
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
        movieId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getSimilarMoviesUseCase(movieId = movieId).map { tVShow -> tVShow.toMovieUiState() }
            },
            onSuccess = ::updateMoreLikeThisSectionWithNewData,
            onError = ::updateRowSectionStateToError
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
        movieId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                movieReviewUseCase(movieId).map { review -> review.toReviewUiState() }
            },
            onSuccess = ::updateReviewRowSectionWithNewData,
            onError = ::updateRowSectionStateToError,
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
        movieId: Long,
    ) {
        updateRowSectionToLoading()
        tryToCall(
            call = {
                getMovieGalleryUseCase(movieId).backdrops
            },
            onSuccess = ::updateMediaGellarySectionWithNewImages,
            onError = ::updateRowSectionStateToError
        )
    }

    private fun updateMediaGellarySectionWithNewImages(backdrops: List<String>) {
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

    override fun onShowCastClicked(movieId: Long) = sendNewEffect(
        MovieDetailsScreenEffect.NavigateToShowAllCastScreen(movieId = movieId)
    )

    override fun onMediaCardClicked(movieId: Long) =
        sendNewEffect(MovieDetailsScreenEffect.NavigateToMovieDetailsScreen(movieId))

    override fun onRateIconClicked(movieId: Long) =
        sendNewEffect(MovieDetailsScreenEffect.ShowRatingDialog(movieId))


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

    fun toggleMovieDetailsTab(
        movieDetailsTabs: MovieDetailsTabs,
        movieId: Long,
    ) {
        updateState { screenState ->
            if (screenState.movieDetailsTabsUiState.tab == movieDetailsTabs) return@updateState screenState

            when (movieDetailsTabs) {
                MovieDetailsTabs.MORE_LIKE_THIS -> onShowMoreMediaLikeThisClicked(movieId = movieId)
                MovieDetailsTabs.REVIEWS -> onShowReviewsClicked(movieId = movieId)
                MovieDetailsTabs.GALLERY -> onShowMediaGalleryClicked(movieId = movieId)
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
            screenState.copy()
        }
    }

    private fun updateRowSectionStateToError(errorState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                rowSection = MoviesRowSectionUiState.Error(
                    errorState.message
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
}