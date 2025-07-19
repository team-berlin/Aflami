package com.berlin.aflami.screens.mediadetails.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.berlin.aflami.component.Chips
import com.berlin.aflami.component.CircularIConButton
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.Rating
import com.berlin.aflami.screens.mediadetails.components.CompanyProductionSection
import com.berlin.aflami.screens.mediadetails.components.GallerySection
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.MediaCastItem
import com.berlin.aflami.screens.mediadetails.components.MoreLikeThisSection
import com.berlin.aflami.screens.mediadetails.components.ReviewsSection
import com.berlin.aflami.screens.mediadetails.components.SeasonsSection
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsScreenEffect
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.mediadetails.MediaInteractionListener
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.mediadetails.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.TabContent
import com.berlin.aflami.viewmodel.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.designsystem.R
import com.example.navigation.Destination
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    viewModel: MediaDetailsViewmodel = koinViewModel(),
    mediaId: Long,
    mediaType: MediaType,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val rowUiState by viewModel.rowSectionUiState.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val tabSelected by viewModel.tabSelectedUiState.collectAsState()
    var showLoginRequiredDialog by remember { mutableStateOf(false) }

    LaunchedEffect(mediaId, mediaType) {
        viewModel.getMediaDetails(mediaId, mediaType)
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                MediaDetailsScreenEffect.NavigateToShowAllCastScreen -> {
                    navController.navigate(Destination.CastScreen.route)
                }
                MediaDetailsScreenEffect.NavigateBack -> {navController.popBackStack()}
                is MediaDetailsScreenEffect.PlayMedia -> {}
                is MediaDetailsScreenEffect.ShowAddToFavoriteListSheet -> {showLoginRequiredDialog = true}
                is MediaDetailsScreenEffect.ShowRatingSheet -> { showLoginRequiredDialog = true}
            }
        }

    }

    if (loading) {
        // Your loader
    } else if (error != null) {
        // Your error UI
    } else {
        MediaDetailsContent(
            state = uiState,
            rowUiState = rowUiState,
            listener = viewModel,
            onToggleExpand = { viewModel.onReadMoreDescriptionClicked(mediaId) },
            isExpanded = viewModel.isDescriptionExpanded(mediaId),
            isSelectedTab = tabSelected.tab,
            onChipClick = { tab ->
                viewModel.toggleMovieDetailsTab(
                    tab = tab,
                    mediaId = 155,
                    mediaType = MediaType.TV_SHOW
                )
            },

            mediaType = mediaType,
        )

        if (showLoginRequiredDialog) {
            LoginRequiredDialog(
                onLoginClick = {
                    showLoginRequiredDialog = false
                    //navController.navigate("login")
                },
                onDismiss = { showLoginRequiredDialog = false },
                title = "Login Required",
                description = "Please login to access your account details\nand other features!"
            )
        }
    }
}

@Composable
fun MediaDetailsContent(
    state: MediaDetailsUiState,
    rowUiState: RowSectionUiState,
    listener: MediaInteractionListener,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    isSelectedTab: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
    mediaType: MediaType,
) {
    LazyColumn(
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
                    AsyncImage(
                        model = state.backdropUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(com.berlin.ui.R.drawable.place_holder),
                        error = painterResource(com.berlin.ui.R.drawable.place_holder),
                    )
                    DefaultBar(
                        modifier = Modifier.statusBarsPadding(),
                        firstOption = painterResource(R.drawable.ic_rounded_star),
                        lastOption = painterResource(R.drawable.ic_rounded_add_heart),
                        onFirstOptionClicked = { listener.onRateIconClicked(state.id) },
                        onLastOptionClicked = { listener.onAddMediaToFavouriteListClicked(0, state.id.toInt()) },
                        onNavigateBackClicked = { listener.onBackClicked() },
                        optionContainerColor = Theme.color.surfaceHigh
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(4.dp)
                    ) {
                        Rating(rating = state.rating.toString())
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
                    text = "Description",
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.small,
                )

                ExpandableDescription(
                    text = state.overview,
                    expanded = isExpanded,
                    onToggleExpand = onToggleExpand,
                    previewColor = Theme.color.textColors.hint,
                    suffixColor = Theme.color.primary,
                    previewStyle = Theme.textStyle.body.small,
                    suffixStyle = Theme.textStyle.label.medium
                )
            }
        }
//        Cast(
//            castState = state.mediaCast,
//            listener = listener
//        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = Theme.color.stroke,
            thickness = 1.dp
        )

        RowSection(
            rowUiState = rowUiState,
            isSelectedTab = isSelectedTab,
            onChipClick = onChipClick,
            isExpanded = isExpanded,
            onToggleExpand = onToggleExpand,
        )
    }
}

@Composable
fun RowSection(
    rowUiState: RowSectionUiState,
    isSelectedTab: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(96.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(MovieDetailsTabs.entries) { tab ->
            Chips(
                title = stringResource(movieDetailsTabsMapper(tab)),
                icon = painterResource(getMovieDetailsTabsIcon(tab)),
                isSelected = tab == isSelectedTab,
                onClick = { onChipClick(tab) }
            )
        }
    }

    when (rowUiState) {
        is RowSectionUiState.Error -> {
            Text(
                text = rowUiState.message,
                style = Theme.textStyle.label.large,
                color = Theme.color.textColors.body,
            )
        }

        is RowSectionUiState.Loading -> {
            Loading()
        }

        is RowSectionUiState.Success -> {
            when (rowUiState.content) {
                is TabContent.MoreLikeThis -> {
                    MoreLikeThisSection(
                        mediaList = (rowUiState.content as TabContent.MoreLikeThis).items,
                        mediaType = MediaType.MOVIE
                    )
                }

                is TabContent.Reviews -> {
                    ReviewsSection(
                        reviews = (rowUiState.content as TabContent.Reviews).items,
                        isExpanded = isExpanded,
                        onToggleExpand = onToggleExpand
                    )
                }

                is TabContent.Gallery -> {
                    GallerySection(
                        mediaImages = (rowUiState.content as TabContent.Gallery).items,
                    )
                }

                is TabContent.CompanyProduction -> {
                    CompanyProductionSection(companyProductions = (rowUiState.content as TabContent.CompanyProduction).items)
                }

                is TabContent.Season -> {
                    Log.d("Khiary", "seasons tab clicked")
                    SeasonsSection(
                        seasonsMap = (rowUiState.content as TabContent.Season).items,
                    )
                }
            }
        }
    }

}

@Composable
fun ExpandableDescription(
    text: String,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    maxPreviewLength: Int = 180,
    previewColor: Color,
    suffixColor: Color,
    previewStyle: TextStyle,
    suffixStyle: TextStyle,
) {
    val canExpand = text.length > maxPreviewLength

    val displayText =
        if (expanded || !canExpand) text else text.take(maxPreviewLength).trimEnd()

    val suffix = when {
        expanded && canExpand -> " Read less"
        !expanded && canExpand -> " Read more"
        else -> ""
    }

    val annotated = buildAnnotatedString {
        append(displayText)
        if (suffix.isNotEmpty()) {
            withStyle(
                SpanStyle(
                    color = suffixColor,
                    fontFamily = suffixStyle.fontFamily,
                    fontWeight = suffixStyle.fontWeight,
                    fontSize = suffixStyle.fontSize
                )
            ) {
                append(suffix)
            }
        }
    }

    Text(
        text = annotated,
        color = previewColor,
        style = previewStyle,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.clickable(
            enabled = canExpand,
            onClick = onToggleExpand
        ),
        textAlign = TextAlign.Start
    )
}

@Composable
fun Cast(
    modifier: Modifier = Modifier,
    castState: List<MediaCastUiState>,
    listener: MediaInteractionListener,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
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
                        .clickable {
                            listener.onShowCastClicked()
                        }
                )
            }
        }

        BoxWithConstraints {
            val screenWidth = this.maxWidth
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

fun movieDetailsTabsMapper(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> R.string.more_like_this
        MovieDetailsTabs.REVIEWS -> R.string.reviews
        MovieDetailsTabs.GALLERY -> R.string.gallery
        MovieDetailsTabs.COMPANY_PRODUCTION -> R.string.company_production
        MovieDetailsTabs.SEASON -> R.string.season
    }
}

fun getMovieDetailsTabsIcon(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> com.berlin.ui.R.drawable.camera_video
        MovieDetailsTabs.REVIEWS -> R.drawable.star
        MovieDetailsTabs.GALLERY -> com.berlin.ui.R.drawable.album
        MovieDetailsTabs.COMPANY_PRODUCTION -> com.berlin.ui.R.drawable.city
        MovieDetailsTabs.SEASON -> com.berlin.ui.R.drawable.season
    }
}