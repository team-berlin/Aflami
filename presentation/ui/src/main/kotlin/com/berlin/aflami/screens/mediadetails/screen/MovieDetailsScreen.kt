package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.SnackBar
import com.berlin.aflami.component.SnackBarStatus
import com.berlin.aflami.navigation.CastDestination
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.navigation.VideoWebViewDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.listdetails.component.AddToListDialog
import com.berlin.aflami.screens.listdetails.component.CreateNewListDialog
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.MovieBackdropPager
import com.berlin.aflami.screens.mediadetails.components.RateDialog
import com.berlin.aflami.screens.mediadetails.components.screensections.CastSection
import com.berlin.aflami.screens.mediadetails.components.screensections.DescriptionSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MediaOverviewSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MovieTabSection
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.base.MovieAlreadyExistInList
import com.berlin.aflami.viewmodel.details.common.MediaDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsScreenEffect
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsTabs
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsUiState
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsViewModel
import com.berlin.aflami.viewmodel.details.movie.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.details.movie.SNACK_BAR_STATUS
import com.berlin.aflami.viewmodel.details.movie.UiText
import com.berlin.aflami.viewmodel.details.series.TVShowRowSectionUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val navController = Theme.navController
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            onReceiveMovieDetailsEffect(
                navController = navController, mediaDetailsScreenEffect = newEffect,
            )
        }
    }


    AnimatedVisibility(
        enter = fadeIn(), exit = fadeOut(), visible = uiState.isScreenLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(), text = stringResource(com.berlin.ui.R.string.loading)
        )
    }
    AnimatedVisibility(
        visible = uiState.errorMessage != null
    ) {
        NoInternetConnectionPlaceholder()
    }

    AnimatedVisibility(
        enter = fadeIn(), exit = fadeOut(), visible = !uiState.isScreenLoading
    ) {
        MovieDetailsContent(
            state = uiState,
            listener = viewModel,
            isDescriptionExpanded = uiState.isDescriptionExpanded,
            onToggleDescriptionExpand = { viewModel.onReadMoreDescriptionClicked() },
            movieDetailsTabs = uiState.movieDetailsTabsUiState.tab,
            onChipClick = { tab ->
                viewModel.toggleMovieDetailsTab(
                    movieDetailsTabs = tab,
                    movieId = uiState.movieUiState.id,
                )
            },
        )
    }

    AnimatedVisibility(
        visible = uiState.snackBarMessage != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val status =
            when(uiState.isSnackBarStatusSuccess){
                true -> SnackBarStatus.SUCCESS
                false -> SnackBarStatus.ERROR
                else -> SnackBarStatus.ERROR
            }
        val icon = when (status) {
            SnackBarStatus.SUCCESS -> painterResource(id = R.drawable.success)
            SnackBarStatus.ERROR -> painterResource(id = R.drawable.error)
        }
        Box(Modifier.statusBarsPadding()) {
            SnackBar(
                status = status,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = uiState.snackBarMessage.orEmpty(),
                iconPainter = icon
            )
        }
    }

    AnimatedVisibility(
        enter = fadeIn(), exit = fadeOut(), visible = uiState.showLoginDialog
    ) {
        LoginRequiredDialog(
            onLoginClick = {
                viewModel.onLoginButtonClicked()
            },
            onDismiss = {viewModel.onLoginDialogDismissed() },
            title = stringResource(com.berlin.ui.R.string.login_required),
            description = stringResource(com.berlin.ui.R.string.login_required_warning)
        )
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = uiState.createNewListDialog.isCreateNewListDialogVisible
    ) {
        CreateNewListDialog(
            listName = uiState.createNewListDialog.newListTitle,
            onListNameChanged = viewModel::onUpdateNewListTitle,
            onCreateListClick = viewModel::onCreateNewListClicked,
            onDismiss = viewModel::onCancelCreatingNewListClicked,
        )
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = uiState.addToListDialog.isAddToListDialogVisible
    ) {
        AddToListDialog(
            movieId = uiState.movieUiState.id,
            addToListUiState = uiState.addToListDialog,
            onSelectedListChange = viewModel::onSelectFavouriteList,
            onAddToSelectedList = viewModel::onAddMediaToFavouriteButtomClicked,
            onCreateNewList = viewModel::onCreateNewFavouriteListClicked,
            onDismiss = viewModel::onCancelAddingToFavouriteClicked,
        )
    }
}


private fun onReceiveMovieDetailsEffect(
    navController: NavController,
    mediaDetailsScreenEffect: MovieDetailsScreenEffect,
) {
    when (mediaDetailsScreenEffect) {

        is MovieDetailsScreenEffect.NavigateToShowAllCastScreen -> {
            navController.navigate(
                CastDestination(
                    mediaDetailsScreenEffect.movieId, MediaType.MOVIE
                )
            )
        }

        is MovieDetailsScreenEffect.NavigateBack -> {
            navController.popBackStack()
        }

        is MovieDetailsScreenEffect.PlayMedia -> {
            navController.navigate(
                VideoWebViewDestination(mediaDetailsScreenEffect.videoUrl)
            )
        }

        is MovieDetailsScreenEffect.ShowRatingDialog -> {}
        is MovieDetailsScreenEffect.NavigateToMovieDetailsScreen -> {
            navController.navigate(
                MovieDetailsDestination(
                    mediaDetailsScreenEffect.movieId
                )
            ) {
                popUpTo(MovieDetailsDestination(movieId = mediaDetailsScreenEffect.movieId)) {
                    inclusive = true

                }
            }
        }

        MovieDetailsScreenEffect.NavigateToLogin -> {
            navController.navigate(
                LoginDestination
            )
        }
    }
}

@Composable
fun MovieDetailsContent(
    state: MovieDetailsUiState,
    listener: MediaDetailsScreenInteractionListener,
    isDescriptionExpanded: Boolean,
    onToggleDescriptionExpand: () -> Unit,
    movieDetailsTabs: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
//    mediaType: MediaType,
) {
    val listState = rememberLazyListState()
    val appBarFadeHeightPx = with(LocalDensity.current) { 50.dp.roundToPx() }
    val appBarAlpha by remember {
        derivedStateOf {
            val offset =
                if (listState.firstVisibleItemIndex == 0) listState.firstVisibleItemScrollOffset else appBarFadeHeightPx
            (offset / appBarFadeHeightPx.toFloat()).coerceIn(0f, 1f)
        }
    }
    val animatedAppBarAlpha by animateFloatAsState(appBarAlpha)
    val appBarBgColor = Theme.color.surface.copy(alpha = animatedAppBarAlpha)

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        LazyColumn(modifier = Modifier.zIndex(0f), state = listState) {
            item {
                MovieBackdropPager(
                    state = state, onPlayClick = { listener.onPlayClicked(state.videoUrl) })
            }

            item {
                with(state.movieUiState) {
                    MediaOverviewSection(
                        title = title,
                        generes = genre,
                        releaseDate = releaseDate,
                        duration = duration,
                        originalCountry = originCountry,
                        numberOfSeasons = null
                    )
                }
            }
            item {
                DescriptionSection(
                    state.movieUiState.description,
                    isExpanded = isDescriptionExpanded,
                    onToggleExpand = onToggleDescriptionExpand
                )
            }
            item {
                CastSection(
                    cast = state.castList,
                    onShowAllClicked = { listener.onShowCastClicked(state.movieUiState.id) })
            }
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    color = Theme.color.stroke,
                    thickness = 1.dp
                )
            }
            item {
                MovieTabSection(
                    movieDetailsTabs = movieDetailsTabs,
                    rowState = state.rowSection,
                    onChipClick = onChipClick,
                    isReviewExpanded = { id -> state.expandedReviewIds.contains(id) },
                    onToggleReviewExpand = { id -> listener.onReadMoreReviewClicked(id) },
                    onMovieCardClicked = { mediaId ->
                        listener.onMediaCardClicked(mediaId)
                    },
                )
            }
        }
        DefaultBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appBarBgColor)
                .statusBarsPadding()
                .zIndex(1f),

            firstOption = painterResource(R.drawable.ic_rounded_star),
            lastOption = painterResource(R.drawable.ic_rounded_add_heart),
            onFirstOptionClicked = { listener.onRateIconClicked(state.movieUiState.id) },
            onLastOptionClicked = {
                listener.onAddMovieToFavouriteClicked()
            },
            onNavigateBackClicked = { listener.onBackClicked() },
            optionContainerColor = Theme.color.surfaceHigh,
            containerColor = Color.Unspecified,
        )
        if (state.showRatingDialog && state.selectedRatingMovieId != null) {
            RateDialog(
                onDismiss = { listener.onCancelRatingClicked() },
                onRate = { rating -> listener.onSubmitRateClicked(rating) })
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .zIndex(10f)
        ) {
            when (state.snackBar.snackBarStatus) {
                SNACK_BAR_STATUS.ADD_MOVIE_TO_LIST -> {
                    if (state.snackBar.isOperationSucceeded) {
                        SnackBar(
                            isVisible = state.snackBar.isVisible,
                            status = SnackBarStatus.SUCCESS,
                            text = stringResource(com.berlin.ui.R.string.movie_added_success),
                            iconPainter = painterResource(id = R.drawable.success),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = {
                                listener.dismissSnackBar()
                            })
                    } else {
                        val errorMessage =
                            if (state.snackBar.errorUiState is MovieAlreadyExistInList)
                                stringResource(
                                    com.berlin.ui.R.string.movie_already_exists
                                ) else stringResource(
                                com.berlin.ui.R.string.movie_added_failed
                            )
                        SnackBar(
                            isVisible = state.snackBar.isVisible,
                            status = SnackBarStatus.ERROR,
                            text = errorMessage,
                            iconPainter = painterResource(id = R.drawable.error),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = {
                                listener.dismissSnackBar()
                            })
                    }
                }

                SNACK_BAR_STATUS.CREATE_NEW_LIST -> if (state.snackBar.isOperationSucceeded) {
                    SnackBar(
                        isVisible = state.snackBar.isVisible,
                        status = SnackBarStatus.SUCCESS,
                        text = stringResource(com.berlin.ui.R.string.new_list_created),
                        iconPainter = painterResource(id = R.drawable.success),
                        modifier = Modifier.align(Alignment.TopCenter),
                        onDismiss = { listener.dismissSnackBar() })
                } else {
                    SnackBar(
                        isVisible = state.snackBar.isVisible,
                        status = SnackBarStatus.ERROR,
                        text = stringResource(com.berlin.ui.R.string.create_new_list_failed),
                        iconPainter = painterResource(id = R.drawable.error),
                        modifier = Modifier.align(Alignment.TopCenter),
                        onDismiss = {
                            listener.dismissSnackBar()
                        })
                }

                SNACK_BAR_STATUS.LIST_DELETED -> {}
                SNACK_BAR_STATUS.LIST_RENAMED -> {}
                null -> {}
            }
        }


    }

}

@Composable
fun MoviesRowSectionUiState.getDisplayMessage(): String {
    return when (this) {
        is MoviesRowSectionUiState.NoDataFound -> this.message.asString()
        else -> "Unknown error!"
    }
}

@Composable
fun TVShowRowSectionUiState.getDisplayMessage(): String {
    return when (this) {
        is TVShowRowSectionUiState.Error -> this.message.orEmpty()
        is TVShowRowSectionUiState.NoDataFound -> this.message.asString()
        else -> "Unknown error!"
    }
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.Dynamic -> value
        is UiText.Resource -> stringResource(id = resId)
    }
}