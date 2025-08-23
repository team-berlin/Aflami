package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.berlin.aflami.component.Chips
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.SnackBarStatus
import com.berlin.aflami.extension.dropShadow
import com.berlin.aflami.navigation.CastDestination
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.navigation.VideoWebViewDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.RateDialog
import com.berlin.aflami.screens.mediadetails.components.TVShowBackdropPager
import com.berlin.aflami.screens.mediadetails.components.getTVShowDetailsTabsIcon
import com.berlin.aflami.screens.mediadetails.components.screensections.CastSection
import com.berlin.aflami.screens.mediadetails.components.screensections.DescriptionSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MediaOverviewSection
import com.berlin.aflami.screens.mediadetails.components.seasonItem
import com.berlin.aflami.screens.mediadetails.components.tvShowDetailsTabsMapper
import com.berlin.aflami.ui.color.ExtraColors.darkReddishGreen12
import com.berlin.aflami.ui.color.ExtraColors.darkReddishPink12
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.common.MediaDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.details.common.SNACK_BAR_STATUS
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsTabs
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsUiState
import com.berlin.aflami.viewmodel.details.series.TvShowDetailsScreenEffect
import com.berlin.aflami.viewmodel.details.series.TvShowDetailsScreenViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R
import kotlinx.coroutines.delay

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
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = uiState.errorMessage != null
    ) {
        NoInternetConnectionPlaceholder(
            onClick = {
                viewModel.retry()
            }
        )
    }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = uiState.isScreenLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(com.berlin.ui.R.string.loading)
        )
    }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = !uiState.isScreenLoading && uiState.errorMessage == null
    ) {
        TvShowDetailsContent(
            state = uiState,
            listener = viewModel,
            isDescriptionExpanded = uiState.isDescriptionExpanded,
            onToggleDescriptionExpand = { viewModel.onReadMoreDescriptionClicked() },
            movieDetailsTabs = uiState.tvShowDetailsTabsUiState.tab,
            onChipClick = { tab ->
                viewModel.toggleTvShowDetailsTab(
                    tvShowDetailsTabs = tab,
                    tvShowId = uiState.tvShowUiState.id,
                )
            },
        )
    }
    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = uiState.showLoginDialog
    ) {
        LoginRequiredDialog(
            onLoginClick = {
                viewModel.onLoginButtonClicked()
            },
            onDismiss = { viewModel.onLoginDialogDismissed() },
            title = stringResource(com.berlin.ui.R.string.login_required),
            description = stringResource(com.berlin.ui.R.string.login_required_warning)
        )
    }


}

private fun onReceiveTVShowDetailsEffect(
    navController: NavController,
    tvShowDetailsScreenEffect: TvShowDetailsScreenEffect,
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
                popUpTo(MovieDetailsDestination(movieId = tvShowDetailsScreenEffect.tvShowId)) {
                    inclusive = true
                }

            }
        }

        TvShowDetailsScreenEffect.NavigateToLogin -> {
            navController.navigate(
                LoginDestination
            )
        }
    }
}

@Composable
fun TvShowDetailsContent(
    state: TVShowDetailsUiState,
    listener: MediaDetailsScreenInteractionListener,
    isDescriptionExpanded: Boolean,
    onToggleDescriptionExpand: () -> Unit,
    movieDetailsTabs: TVShowDetailsTabs,
    onChipClick: (TVShowDetailsTabs) -> Unit,
) {
    val expandedStates = remember {
        mutableStateMapOf<Int, Boolean>()
    }
    val listState = rememberLazyListState()
    val appBarFadeHeightPx = with(LocalDensity.current) { 50.dp.roundToPx() }
    val appBarAlpha by remember {
        derivedStateOf {
            val offset =
                if (listState.firstVisibleItemIndex == 0)
                    listState.firstVisibleItemScrollOffset
                else appBarFadeHeightPx
            (offset / appBarFadeHeightPx.toFloat()).coerceIn(0f, 1f)
        }
    }
    val animatedAppBarAlpha by animateFloatAsState(appBarAlpha)
    val appBarBgColor = Theme.color.surface.copy(alpha = animatedAppBarAlpha)

    var offset by remember { mutableFloatStateOf(0f) }
    val marginTopInPx = with(LocalDensity.current) { 0.dp.toPx() }
    val maxOffsetInPx = with(LocalDensity.current) { 80.dp.toPx() }
    val startScrollThresholdPx = with(LocalDensity.current) { 20.dp.toPx() }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = -available.y

                if (listState.firstVisibleItemIndex == 0 &&
                    listState.firstVisibleItemScrollOffset > startScrollThresholdPx
                ) {
                    offset = (offset + delta).coerceIn(marginTopInPx, maxOffsetInPx)
                }

                return Offset.Zero
            }
        }
    }


    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surface)

    ) {

        Box(
            Modifier
                .nestedScroll(nestedScrollConnection)
                .padding(top = with(LocalDensity.current) { offset.toDp() })
        ) {
            LazyColumn(state = listState)
            {
                item {
                    TVShowBackdropPager(
                        state = state,
                        onPlayClick = { state.videoUrl?.let { listener.onPlayClicked(it) } })
                }

                item {
                    with(state.tvShowUiState) {
                        MediaOverviewSection(
                            title = title,
                            generes = genre,
                            releaseDate = releaseDate,
                            originalCountry = originCountry,
                            numberOfSeasons = numberOfSeasons
                        )
                    }
                }
                if (state.tvShowUiState.description.isNotEmpty()) {
                    item {
                        DescriptionSection(
                            state.tvShowUiState.description, isExpanded = isDescriptionExpanded,
                            onToggleExpand = onToggleDescriptionExpand
                        )
                    }
                }
                if (state.castList.isNotEmpty()) {
                    item {
                        CastSection(
                            cast = state.castList,
                            onShowAllClicked = { listener.onShowCastClicked(state.tvShowUiState.id) }
                        )
                    }
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
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(TVShowDetailsTabs.entries, key = { it.name }) { tab ->
                            Chips(
                                title = stringResource(tvShowDetailsTabsMapper(tab)),
                                icon = painterResource(getTVShowDetailsTabsIcon(tab)),
                                isSelected = tab == movieDetailsTabs,
                                onClick = { onChipClick(tab) }
                            )
                        }
                    }
                }

                // region TVShowRowSection
                tvShowRowSectionUiStateError(
                    state = state.rowSection,
                    listener = listener
                )
                tvShowRowSectionUiStateLoading(
                    state = state.rowSection,
                    listener = listener
                )
                moreLikeThisSection(
                    state = state.rowSection,
                    listener = listener
                )
                reviewSection(
                    state = state.rowSection,
                    listener = listener,
                    tvShowDetailsUiState = state
                )

                gallerySection(
                    state = state.rowSection,
                    listener = listener
                )
                companyProductionSection(
                    state = state.rowSection,
                    listener = listener
                )

                seasonItem(
                    state = state.rowSection,
                    expandedStates = expandedStates,
                    listener = listener
                )
                //endregion
            }
        }

        DefaultBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appBarBgColor)
                .statusBarsPadding(),
            firstOption = painterResource(R.drawable.ic_rounded_star),
            onFirstOptionClicked = { listener.onRateIconClicked(state.tvShowUiState.id) },
            onNavigateBackClicked = { listener.onBackClicked() },
            optionContainerColor = Theme.color.surfaceHigh,
            containerColor = Color.Unspecified,
        )
            SnackBar(
                isVisible =state.noTrailerForEpisode
                ,
                text = stringResource(com.berlin.ui.R.string.no_trailer_for_episode),
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                status = SnackBarStatus.ERROR,
                iconPainter = painterResource(id = R.drawable.error),
                onDismiss = {
                    listener.dismissSnackBar()
                })
    }

    if (state.showRatingDialog && state.selectedRatingMediaId != null) {
        RateDialog(
            onDismiss = { listener.onCancelRatingClicked() },
            onRate = { rating -> listener.onSubmitRateClicked(rating) }
        )
    }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .zIndex(10f)
    ) {
        when (state.snackBar.snackBarStatus) {

            SNACK_BAR_STATUS.RATING_ADDED -> if (state.snackBar.isOperationSucceeded) {
                SnackBar(
                    isVisible = state.snackBar.isVisible,
                    status = SnackBarStatus.SUCCESS,
                    text = stringResource(com.berlin.ui.R.string.rating_added),
                    iconPainter = painterResource(id = R.drawable.success),
                    modifier = Modifier.align(Alignment.TopCenter),
                    onDismiss = { listener.dismissSnackBar() })
            } else {
                SnackBar(
                    isVisible = state.snackBar.isVisible,
                    status = SnackBarStatus.ERROR,
                    text = stringResource(com.berlin.ui.R.string.rating_added_failed),
                    iconPainter = painterResource(id = R.drawable.error),
                    modifier = Modifier.align(Alignment.TopCenter),
                    onDismiss = {
                        listener.dismissSnackBar()
                    })
            }

            else -> {}
        }
    }

}
@Composable
private fun SnackBar(
    isVisible: Boolean = false,
    status: SnackBarStatus,
    text: String,
    iconPainter: Painter,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2000,
    onDismiss: () -> Unit = {},
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            visible = true
            delay(durationMillis)
            visible = false
            onDismiss()
        }
    }
    AnimatedVisibility(
        visible = visible,
        modifier=modifier,
        enter = fadeIn() + slideInVertically(initialOffsetY = {  it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    alpha = 0.12f,
                    offsetX = 0.dp,
                    offsetY = (4).dp,
                    blur = 6.dp,
                    color = when (status) {
                        SnackBarStatus.SUCCESS -> darkReddishGreen12
                        SnackBarStatus.ERROR -> darkReddishPink12
                    },
                )
        ) {
            Row(
                modifier
                    .fillMaxWidth(0.95f)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = Theme.color.stroke,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(Theme.color.surfaceHigh)
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = stringResource(R.string.icon_cd),
                    tint = when (status) {
                        SnackBarStatus.SUCCESS -> Theme.color.statusColors.greenAccent
                        SnackBarStatus.ERROR -> Theme.color.statusColors.redAccent
                    },
                )

                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = Theme.textStyle.body.medium,
                    color = Theme.color.textColors.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}