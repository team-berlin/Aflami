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
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.navigation.VideoWebViewDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.mediadetails.components.TVShowBackdropPager
import com.berlin.aflami.screens.mediadetails.components.screensections.CastSection
import com.berlin.aflami.screens.mediadetails.components.screensections.DescriptionSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MediaOverviewSection
import com.berlin.aflami.screens.mediadetails.components.screensections.TVShowTabSection
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.common.MediaInteractionListener
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsTabs
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsUiState
import com.berlin.aflami.viewmodel.details.series.TvShowDetailsScreenEffect
import com.berlin.aflami.viewmodel.details.series.TvShowDetailsScreenViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R

@Composable
fun TvShowDetailsScreen(
    viewModel: TvShowDetailsScreenViewModel = hiltViewModel(),
) {

        val navController = Theme.navController
        val uiState by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.effect.collect { newEffect ->
                onReceiveTVShowDetailsEffect(
                    navController = navController,
                    tvShowDetailsScreenEffect = newEffect
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
            TvShowDetailsContent(
                state = uiState,
                listener = viewModel,
                isDescriptionExpanded = uiState.isDescriptionExpanded,
                onToggleDescriptionExpand = { viewModel.onReadMoreDescriptionClicked() },
                movieDetailsTabs = uiState.tvShowDetailsTabsUiState.tab,
                onChipClick = { tab ->
                    viewModel.toggleTvShowDetailsTab(
                        tvShowDetailsTabs = uiState.tvShowDetailsTabsUiState.tab,
                        tvShowId = uiState.tvShowUiState.id,
                    )
                },
            )
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = false
        ) {
//        LoginRequiredDialog(
//            onLoginClick = {
////                viewModel.showLoginDialog(false)
//            },
////            onDismiss = { viewModel.showLoginDialog(false) },
//            title = stringResource(com.berlin.ui.R.string.login_required),
//            description = stringResource(com.berlin.ui.R.string.login_required_warning)
//        )
        }
    }

    private fun onReceiveTVShowDetailsEffect(
        navController: NavController,
        tvShowDetailsScreenEffect: TvShowDetailsScreenEffect
    ) {
        when (tvShowDetailsScreenEffect) {
            is TvShowDetailsScreenEffect.NavigateToShowAllCastScreen -> {
                navController.navigate(
                    CastDestination(
                        tvShowDetailsScreenEffect.tvShowId,
                        MediaType.TV_SHOW
                    )
                )
            }

            is TvShowDetailsScreenEffect.NavigateBack -> {
                navController.popBackStack()
            }

            is TvShowDetailsScreenEffect.PlayMedia -> {
                navController.navigate(
                    VideoWebViewDestination(tvShowDetailsScreenEffect.videoUrl)
                )
            }

            is TvShowDetailsScreenEffect.ShowAddToFavoriteListDialog -> {}
            is TvShowDetailsScreenEffect.ShowRatingDialog -> {}
            is TvShowDetailsScreenEffect.NavigateToMediaDetailsScreen -> {
                navController.navigate(
                    TVShowDetailsDestination(
                        tvShowDetailsScreenEffect.tvShowId
                    )
                ) {
                    launchSingleTop = true
                }
            }

        }
    }

    @Composable
    fun TvShowDetailsContent(
        state: TVShowDetailsUiState,
        listener: MediaInteractionListener,
        isDescriptionExpanded: Boolean,
        onToggleDescriptionExpand: () -> Unit,
        movieDetailsTabs: TVShowDetailsTabs,
        onChipClick: (TVShowDetailsTabs) -> Unit,
//
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
                    TVShowBackdropPager(
                        state = state,
                        onPlayClick = { listener.onPlayClicked(state.videoUrl) })
                }

                item {
                    with(state.tvShowUiState) {
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
                        state.tvShowUiState.description, isExpanded = isDescriptionExpanded,
                        onToggleExpand = onToggleDescriptionExpand
                    )
                }
                item {
                    CastSection(
                        cast = state.castList,
                        onShowAllClicked = { listener.onShowCastClicked(state.tvShowUiState.id) }
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
                    TVShowTabSection(
                        tvShowDetailsTabs = movieDetailsTabs,
                        rowState = state.rowSection,
                        onChipClick = onChipClick,
                        isReviewExpanded = { id -> state.expandedReviewIds.contains(id) },
                        onToggleReviewExpand = { id -> listener.onReadMoreReviewClicked(id) },
                        onTVShowCardClicked = { mediaId ->
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
                onFirstOptionClicked = { listener.onRateIconClicked(state.tvShowUiState.id) },
                onLastOptionClicked = {
                    listener.onAddMediaToFavouriteListClicked(0, state.tvShowUiState.id.toInt())
                },
                onNavigateBackClicked = { listener.onBackClicked() },
                optionContainerColor = Theme.color.surfaceHigh,
                containerColor = Color.Unspecified, // transparent so Modifier.background takes effect
            )
        }

    }
