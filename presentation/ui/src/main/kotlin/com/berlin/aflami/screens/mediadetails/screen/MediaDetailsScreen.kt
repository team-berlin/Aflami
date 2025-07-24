package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.berlin.aflami.component.Chips
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.screens.mediadetails.components.BackdropPager
import com.berlin.aflami.screens.mediadetails.components.CircularDot
import com.berlin.aflami.screens.mediadetails.components.CompanyProductionSection
import com.berlin.aflami.screens.mediadetails.components.ExpandableText
import com.berlin.aflami.screens.mediadetails.components.GallerySection
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.MoreLikeThisSection
import com.berlin.aflami.screens.mediadetails.components.ReviewsSection
import com.berlin.aflami.screens.mediadetails.components.SeasonsSection
import com.berlin.aflami.screens.mediadetails.components.getMovieDetailsTabsIcon
import com.berlin.aflami.screens.mediadetails.components.movieDetailsTabsMapper
import com.berlin.aflami.screens.mediadetails.components.tabsections.CastSection
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.mediadetails.details.MediaInteractionListener
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.TabContent
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
        Loading()
    } else if (uiState.error != null) {
        Box(
            Modifier.padding(top = 32.dp, bottom = 82.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = uiState.error?.asString()?:"",
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
            val offset = if (listState.firstVisibleItemIndex == 0) listState.firstVisibleItemScrollOffset else appBarFadeHeightPx
            (offset / appBarFadeHeightPx.toFloat()).coerceIn(0f, 1f)
        }
    }
    val animatedAppBarAlpha by animateFloatAsState(appBarAlpha)
    val appBarBgColor = Theme.color.surface.copy(alpha = animatedAppBarAlpha)

    Box(Modifier.fillMaxSize()) {
        LazyColumn(state = listState) {
            item { BackdropPager(state = state, onPlayClick = { listener.onPlayClicked(state.id) }) }
            item {
                Spacer(Modifier.height(12.dp))
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = state.title,
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    Spacer(Modifier.height(12.dp))
                    Row {
                        state.genres.forEach { g ->
                            Box(modifier = Modifier.padding(end = 4.dp)) {
                                GenersChip(label = g)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        state.releaseYear,
                        style = Theme.textStyle.label.small,
                        color = Theme.color.textColors.hint
                    )
                    state.duration.takeIf { !it.isNullOrEmpty() }?.let { duration ->
                        CircularDot()
                        Text(
                            duration,
                            style = Theme.textStyle.label.small,
                            color = Theme.color.textColors.hint
                        )
                    }

                    state.numberOfSeasons?.toString()?.let { numberOfSeasons ->
                        CircularDot()
                        Text(
                            "$numberOfSeasons ${stringResource(com.berlin.ui.R.string.season)}",
                            style = Theme.textStyle.label.small,
                            color = Theme.color.textColors.hint
                        )
                    }

                    state.originalCountry.takeIf { !it.isNullOrEmpty() }?.let { originalCountry ->
                        CircularDot()
                        Text(
                            originalCountry,
                            style = Theme.textStyle.label.small,
                            color = Theme.color.textColors.hint
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.height(24.dp))
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = stringResource(com.berlin.ui.R.string.description),
                        color = Theme.color.textColors.title,
                        style = Theme.textStyle.title.small,
                    )

                    ExpandableText(
                        text = state.overview,
                        isExpanded = isDescriptionExpanded,
                        onToggleExpand = onToggleDescriptionExpand,
                        previewColor = Theme.color.textColors.hint,
                        suffixColor = Theme.color.primary,
                        previewStyle = Theme.textStyle.body.small,
                        suffixStyle = Theme.textStyle.label.medium
                    )
                }
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
fun TabSection(
    tabState: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
    rowState: RowSectionUiState,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    mediaType: MediaType
) {
    val visibleTabs = MovieDetailsTabs.entries.filter {
        !(mediaType == MediaType.MOVIE && it == MovieDetailsTabs.SEASON)
    }

    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(96.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(visibleTabs, key = { it.name }) { tab ->
            Chips(
                title = stringResource(movieDetailsTabsMapper(tab)),
                icon = painterResource(getMovieDetailsTabsIcon(tab)),
                isSelected = tab == tabState,
                onClick = { onChipClick(tab) }
            )
        }
    }

    when (val content = rowState) {
        is RowSectionUiState.Error,
        is RowSectionUiState.NoDataFound -> {
            Box(
                Modifier.padding(top = 32.dp, bottom = 82.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = content.getDisplayMessage(),
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.body,
                    textAlign = TextAlign.Center
                )
            }
        }

        is RowSectionUiState.Loading -> Loading()

        is RowSectionUiState.Success -> when (val tab = content.content) {
            is TabContent.MoreLikeThis -> MoreLikeThisSection(mediaList = tab.items, mediaType = mediaType)
            is TabContent.Reviews -> ReviewsSection(reviews = tab.items, isExpanded = isExpanded, onToggleExpand = onToggleExpand)
            is TabContent.Gallery -> GallerySection(mediaImages = tab.items)
            is TabContent.CompanyProduction -> CompanyProductionSection(companyProductions = tab.items)
            is TabContent.Season -> SeasonsSection(seasonsMap = tab.items)
        }
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