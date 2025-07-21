package com.berlin.aflami.screens.mediadetails.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.berlin.aflami.component.Chips
import com.berlin.aflami.component.CircularIConButton
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.Rating
import com.berlin.aflami.screens.mediadetails.components.CompanyProductionSection
import com.berlin.aflami.screens.mediadetails.components.ExpandableText
import com.berlin.aflami.screens.mediadetails.components.GallerySection
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.MediaCastItem
import com.berlin.aflami.screens.mediadetails.components.MoreLikeThisSection
import com.berlin.aflami.screens.mediadetails.components.ReviewsSection
import com.berlin.aflami.screens.mediadetails.components.SeasonsSection
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.utils.formatRating
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.mediadetails.details.MediaInteractionListener
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.TabContent
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R
import com.example.navigation.Destination
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    viewModel: MediaDetailsViewModel = koinViewModel(),
    navController: NavController
) {
    val uiState by viewModel.state.collectAsState()
    val tabSelected by viewModel.tabSelectedUiState.collectAsState()
    val showLoginRequiredDialog by viewModel.showLoginRequiredDialog.collectAsState()
    val navMediaType = com.example.navigation.MediaType.valueOf(viewModel.type.name)

    LaunchedEffect(Unit) {
        viewModel.getMediaCast(viewModel.id, viewModel.type, "US-EG")
        viewModel.getMediaDetails(viewModel.id, viewModel.type, "en-US")

        viewModel.effect.collect { event ->
            when (event) {
                is MediaDetailsScreenEffect.NavigateToShowAllCastScreen -> {
                    navController.navigate(
                        Destination.CastScreen.route(
                            viewModel.id,
                            navMediaType
                        )
                    )
                }

                MediaDetailsScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is MediaDetailsScreenEffect.PlayMedia -> {}
                is MediaDetailsScreenEffect.ShowAddToFavoriteListSheet -> {
                    viewModel.showLoginDialog(true)
                }

                is MediaDetailsScreenEffect.ShowRatingSheet -> {
                    viewModel.showLoginDialog(true)
                }
            }
        }
    }


    if (uiState.isLoading) {
        Loading()
    } else if (uiState.error != null) {
        // Your error UI
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
                    //navController.navigate("login")
                },
                onDismiss = { viewModel.showLoginDialog(false) },
                title = stringResource(com.berlin.ui.R.string.login_required),
                description = stringResource(com.berlin.ui.R.string.login_required_warning)
            )
        }
    }
}

val PagerState.pageOffset: Float
    get() = currentPage + currentPageOffsetFraction

fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

private fun DrawScope.drawIndicator(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: CornerRadius,
    color: Color
) {
    val rect = RoundRect(
        x - width / 2,
        y - height / 2,
        x + width / 2,
        y + height / 2,
        radius
    )
    val path = Path().apply { addRoundRect(rect) }
    drawPath(path = path, color = color)
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
    mediaType: MediaType,
) {
    val listState = rememberLazyListState()
    val appBarFadeHeightPx = with(LocalDensity.current) { 50.dp.roundToPx() }

    val appBarAlpha by remember {
        derivedStateOf {
            val offset = if (listState.firstVisibleItemIndex == 0)
                listState.firstVisibleItemScrollOffset
            else
                appBarFadeHeightPx
            (offset / appBarFadeHeightPx.toFloat()).coerceIn(0f, 1f)
        }
    }
    val animatedAppBarAlpha by animateFloatAsState(appBarAlpha)
    val appBarBgColor = Theme.color.surface.copy(alpha = animatedAppBarAlpha)

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
        ) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(293.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(263.dp)
                    ) {
                        val pagerState = rememberPagerState(pageCount = { 4 })

                        LaunchedEffect(pagerState) {
                            while (true) {
                                delay(4000)
                                val nextPage = (pagerState.currentPage + 1) % 4
                                pagerState.animateScrollToPage(nextPage)
                            }
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            AsyncImage(
                                model = state.backdropUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Indicator(pagerState)

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(4.dp)
                        ) {
                            Rating(rating = formatRating(state.rating))
                        }
                    }

                    Box(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .size(72.dp)
                            .background(Theme.color.surface, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularIConButton(
                            modifier = Modifier.align(Alignment.Center),
                            painter = painterResource(R.drawable.play_arrow),
                            onClick = { listener.onPlayClicked(state.id) },
                            hasDropShadow = true,
                            dropShadowAlpha = 0.09f,
                            borderWidth = 2,
                            size = 64,
                            enabled = state.hasVideo,
                            tint = if (state.hasVideo) Theme.color.primary else Theme.color.disable,

                            )
                    }
                }
            }

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
                            "$numberOfSeasons ${stringResource(R.string.season)}",
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
                Cast(
                    castState = state.mediaCast,
                    listener = listener
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
                RowSection(
                    uiState = state,
                    isSelectedTab = isSelectedTab,
                    onChipClick = onChipClick,
                    isExpanded = isReviewExpanded,
                    onToggleExpand = onToggleReviewExpand,
                    mediaType = mediaType
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
fun RowSection(
    uiState: MediaDetailsUiState,
    isSelectedTab: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
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
        items(visibleTabs) { tab ->
            Chips(
                title = stringResource(movieDetailsTabsMapper(tab)),
                icon = painterResource(getMovieDetailsTabsIcon(tab)),
                isSelected = tab == isSelectedTab,
                onClick = { onChipClick(tab) }
            )
        }
    }

    when (val sectionState = uiState.rowSection) {
        is RowSectionUiState.Error -> {
            Box(
                Modifier.padding(top = 32.dp, bottom = 82.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = sectionState.message,
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.body,
                    textAlign = TextAlign.Center
                )
            }
        }

        is RowSectionUiState.NoDataFound -> {
            Box(
                Modifier.padding(top = 32.dp, bottom = 82.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = sectionState.message,
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.body,
                    textAlign = TextAlign.Center
                )
            }

        }

        is RowSectionUiState.Loading -> {
            Loading()
        }

        is RowSectionUiState.Success -> {
            when (val content = sectionState.content) {
                is TabContent.MoreLikeThis -> {
                    MoreLikeThisSection(
                        mediaList = content.items,
                        mediaType = mediaType
                    )
                }

                is TabContent.Reviews -> {
                    ReviewsSection(
                        reviews = content.items,
                        onToggleExpand = onToggleExpand,
                        isExpanded = isExpanded,
                    )
                }

                is TabContent.Gallery -> {
                    GallerySection(
                        mediaImages = content.items,
                    )
                }

                is TabContent.CompanyProduction -> {
                    CompanyProductionSection(
                        companyProductions = content.items
                    )
                }

                is TabContent.Season -> {
                    SeasonsSection(
                        seasonsMap = content.items
                    )
                }
            }
        }

    }

}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Cast(
    modifier: Modifier = Modifier,
    castState: List<MediaCastUiState>,
    listener: MediaInteractionListener,
) {
    Column(
        modifier = modifier.padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(com.berlin.ui.R.string.cast),
                style = Theme.textStyle.headline.small,
                color = Theme.color.textColors.title
            )
            Text(
                text = stringResource(com.berlin.ui.R.string.all),
                style = Theme.textStyle.label.medium,
                color = Theme.color.primary,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        listener.onShowCastClicked()
                    }
            )
        }

        BoxWithConstraints {
            val screenWidth = maxWidth
            val cardSize = 78.dp
            val spaceBetween = 8.dp
            val totalCardWidth = cardSize + spaceBetween

            val maxCardsInRow = (screenWidth / totalCardWidth).toInt()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(spaceBetween)
            ) {
                castState.take(maxCardsInRow).forEach {
                    MediaCastItem(
                        modifier = Modifier.size(cardSize),
                        name = it.name,
                        poster = it.poster
                    )
                }
            }
        }

    }
}

@Composable
fun CircularDot() {
    Box(
        modifier = Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(Theme.color.stroke)
    )
}

@Composable
fun BoxScope.Indicator(pagerState: PagerState) {
    val count = 4

    val circleSpacing = 4.dp
    val dotWidth = 8.dp
    val dotHeight = 8.dp
    val activeLineWidth = 24.dp
    val radius = with(LocalDensity.current) { CornerRadius(4.dp.toPx(), 4.dp.toPx()) }
    val activeIndicatorColor = Theme.color.primary
    val inactiveIndicatorColor = Theme.color.textColors.hint
    val backGroundColor = Theme.color.primaryVariant

    Canvas(
        modifier = Modifier
            .width(8.dp)
            .align(Alignment.BottomEnd)
            .padding(end = 8.dp, bottom = 8.dp)
    ) {
        val spacing = circleSpacing.toPx()
        val dotWidthPx = dotWidth.toPx()
        val dotHeightPx = dotHeight.toPx()
        val activeDotHeightPx = activeLineWidth.toPx()
        var y = size.height
        val x = center.x

        drawRoundRect(
            color = backGroundColor,
            size = size,
        )

        repeat(count) { i ->
            val posOffset = pagerState.pageOffset
            val dotOffset = posOffset % 1
            val current = posOffset.toInt()

            val factor = (dotOffset * (activeDotHeightPx - dotHeightPx))

            val calculatedHeight = when {
                i == current -> activeDotHeightPx - factor
                i - 1 == current || (i == 0 && posOffset > count - 1) -> dotHeightPx + factor
                else -> dotHeightPx
            }
            val indicatorColor =
                if (i == current) activeIndicatorColor else inactiveIndicatorColor
            drawIndicator(
                x = x,
                y = y - calculatedHeight / 2,
                width = dotWidthPx,
                height = calculatedHeight,
                radius = radius,
                color = indicatorColor
            )
            y -= calculatedHeight + spacing
        }
    }
}


private fun movieDetailsTabsMapper(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> R.string.more_like_this
        MovieDetailsTabs.REVIEWS -> R.string.reviews
        MovieDetailsTabs.GALLERY -> R.string.gallery
        MovieDetailsTabs.COMPANY_PRODUCTION -> R.string.company_production
        MovieDetailsTabs.SEASON -> R.string.season
    }
}

private fun getMovieDetailsTabsIcon(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> com.berlin.ui.R.drawable.camera_video
        MovieDetailsTabs.REVIEWS -> R.drawable.star
        MovieDetailsTabs.GALLERY -> com.berlin.ui.R.drawable.album
        MovieDetailsTabs.COMPANY_PRODUCTION -> com.berlin.ui.R.drawable.city
        MovieDetailsTabs.SEASON -> com.berlin.ui.R.drawable.season
    }
}
