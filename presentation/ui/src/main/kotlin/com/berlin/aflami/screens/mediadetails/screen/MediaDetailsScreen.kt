package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.screens.mediadetails.components.BackdropPager
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.screensections.CastSection
import com.berlin.aflami.screens.mediadetails.components.screensections.DescriptionSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MediaOverviewSection
import com.berlin.aflami.screens.mediadetails.components.screensections.TabSection
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.mediadetails.details.MediaInteractionListener
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.UiText
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    viewModel: MediaDetailsViewModel = koinViewModel(),
    onEffect: (MediaDetailsScreenEffect) -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val tabSelected by viewModel.tabSelectedUiState.collectAsState()
    val showLoginRequiredDialog by viewModel.showLoginRequiredDialog.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { event ->
            when (event) {
                is MediaDetailsScreenEffect.ShowRatingDialog -> {
                    TODO("Actual implementation once login is in place")
                }

                is MediaDetailsScreenEffect.ShowAddToFavoriteListDialog -> {
                    TODO("Actual implementation once login is in place")
                }

                else -> onEffect(event)
            }
        }
    }

    if (uiState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(com.berlin.ui.R.string.loading)
        )
    } else if (uiState.error != null) {
        Box(
            Modifier.padding(top = 32.dp, bottom = 82.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = uiState.error?:"",
                style = Theme.textStyle.label.large,
                color = Theme.color.textColors.body,
                textAlign = TextAlign.Center
            )
        }
    } else {
        MediaDetailsContent(
            state = uiState,
            listener = viewModel,
            isDescriptionExpanded = viewModel.isDescriptionExpanded(),
            onToggleDescriptionExpand = { viewModel.onReadMoreDescriptionClicked() },
            isReviewExpanded = viewModel.isReviewExpanded(viewModel.id),
            onToggleReviewExpand = { viewModel.onReadMoreReviewClicked(viewModel.id) },
            isSelectedTab = tabSelected.tab,
            onChipClick = { tab ->
                viewModel.toggleMovieDetailsTab(
                    tab = tab,
                    mediaId = viewModel.id,
                    mediaType = viewModel.type,
                )
            },
            mediaType = viewModel.type
        )

        if (showLoginRequiredDialog) {
            LoginRequiredDialog(
                onLoginClick = {
                    viewModel.showLoginDialog(false)
                },
                onDismiss = { viewModel.showLoginDialog(false) },
                title = stringResource(com.berlin.ui.R.string.login_required),
                description = stringResource(com.berlin.ui.R.string.login_required_warning)
            )
        }
    }
}

@Composable
fun MediaDetailsContent(
    state: MediaDetailsUiState,
    listener: MediaInteractionListener,
    isDescriptionExpanded: Boolean,
    onToggleDescriptionExpand: () -> Unit,
    isReviewExpanded: Boolean,
    onToggleReviewExpand: () -> Unit,
    isSelectedTab: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
    mediaType: MediaType
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

    Box(Modifier.fillMaxSize()
        .background(Theme.color.surface)
    ) {
        LazyColumn(state = listState) {
            item {
                BackdropPager(
                    state = state,
                    onPlayClick = { listener.onPlayClicked(state.id) })
            }

            item {
                MediaOverviewSection(state = state)
            }

            item {
                DescriptionSection(
                    state.overview, isExpanded = isDescriptionExpanded,
                    onToggleExpand = onToggleDescriptionExpand
                )
            }

            item {
                CastSection(
                    cast = state.mediaCast,
                    onShowAllClicked = { listener.onShowCastClicked() }
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Theme.color.stroke,
                    thickness = 1.dp
                )
            }

            item {
                TabSection(
                    tabState = isSelectedTab,
                    rowState = state.rowSection,
                    onChipClick = onChipClick,
                    isExpanded = isReviewExpanded,
                    onToggleExpand = onToggleReviewExpand,
                    mediaType = mediaType,
                )
            }
        }
        DefaultBar(
            modifier = Modifier.statusBarsPadding(),
            firstOption = painterResource(R.drawable.ic_rounded_star),
            lastOption = painterResource(R.drawable.ic_rounded_add_heart),
            onFirstOptionClicked = { listener.onRateIconClicked(state.id) },
            onLastOptionClicked = {
                listener.onAddMediaToFavouriteListClicked(
                    0,
                    state.id.toInt()
                )
            },
            onNavigateBackClicked = { listener.onBackClicked() },
            optionContainerColor = Theme.color.surfaceHigh,
            containerColor = appBarBgColor,

            )
    }

}


@Composable
fun RowSectionUiState.getDisplayMessage(): String {
    return when (this) {
        is RowSectionUiState.Error -> this.message.orEmpty()
        is RowSectionUiState.NoDataFound -> this.message.asString()
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