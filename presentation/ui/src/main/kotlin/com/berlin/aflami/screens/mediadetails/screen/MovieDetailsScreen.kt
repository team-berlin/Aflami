package com.berlin.aflami.screens.mediadetails.screen

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.navigation.CastDestination
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.navigation.VideoWebViewDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.MovieBackdropPager
import com.berlin.aflami.screens.mediadetails.components.RateDialog
import com.berlin.aflami.screens.mediadetails.components.screensections.CastSection
import com.berlin.aflami.screens.mediadetails.components.screensections.DescriptionSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MediaOverviewSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MovieTabSection
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.common.MediaInteractionListener
import com.berlin.aflami.viewmodel.details.common.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsScreenEffect
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsUiState
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsTabs
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsViewModel
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
                navController = navController,
                mediaDetailsScreenEffect = newEffect
            )
        }
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = uiState.isScreenLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(com.berlin.ui.R.string.loading)
        )
    }
    AnimatedVisibility(
        visible = uiState.errorMessage != null
    ) {
        NoInternetConnectionPlaceholder()
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !uiState.isScreenLoading
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
        enter = fadeIn(),
        exit = fadeOut(),
        visible = uiState.showLoginDialog
    ) {
        LoginRequiredDialog(
        onLoginClick = {
            viewModel.onLoginButtonClicked()
        },
        onDismiss = { },
        title = stringResource(com.berlin.ui.R.string.login_required),
        description = stringResource(com.berlin.ui.R.string.login_required_warning)
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
                    mediaDetailsScreenEffect.movieId,
                    MediaType.MOVIE
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

        is MovieDetailsScreenEffect.ShowAddToFavoriteListDialog -> {}
        is MovieDetailsScreenEffect.ShowRatingDialog -> {}
        is MovieDetailsScreenEffect.NavigateToMovieDetailsScreen -> {
            navController.navigate(
                MovieDetailsDestination(
                    mediaDetailsScreenEffect.movieId
                )
            ) {
                popUpTo(MovieDetailsDestination(movieId = mediaDetailsScreenEffect.movieId)){
                    inclusive = true

                }
            }
        }

        is MovieDetailsScreenEffect.ShowLoginDialog -> {}
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
    listener: MediaInteractionListener,
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
        LazyColumn(state = listState) {
            item {
                MovieBackdropPager(
                    state = state,
                    onPlayClick = { listener.onPlayClicked(state.videoUrl) })
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
                    state.movieUiState.description, isExpanded = isDescriptionExpanded,
                    onToggleExpand = onToggleDescriptionExpand
                )
            }
            item {
                CastSection(
                    cast = state.castList,
                    onShowAllClicked = { listener.onShowCastClicked(state.movieUiState.id) }
                )
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
                        Log.e("click","click")
                        listener.onMediaCardClicked(mediaId)
                    },
                )
            }
        }
        DefaultBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appBarBgColor)
                .statusBarsPadding(),

            firstOption = painterResource(R.drawable.ic_rounded_star),
            lastOption = painterResource(R.drawable.ic_rounded_add_heart),
            onFirstOptionClicked = { listener.onRateIconClicked(state.movieUiState.id) },
            onLastOptionClicked = {
                listener.onAddMediaToFavouriteListClicked(0, state.movieUiState.id)
            },
            onNavigateBackClicked = { listener.onBackClicked() },
            optionContainerColor = Theme.color.surfaceHigh,
            containerColor = Color.Unspecified,
        )
        if (state.showRatingDialog && state.selectedRatingMediaId != null) {
            RateDialog(
                onDismiss = { listener.onCancelRatingClicked() },
                onRate = { rating -> listener.onSubmitRateClicked(rating) }
            )
        }

    }

}

@Composable
fun MoviesRowSectionUiState.getDisplayMessage(): String {
    return when (this) {
        is MoviesRowSectionUiState.Error -> this.message.orEmpty()
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