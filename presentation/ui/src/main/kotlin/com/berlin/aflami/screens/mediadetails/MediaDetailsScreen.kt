package com.berlin.aflami.screens.mediadetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.component.CircularIConButton
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.Rating
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    viewModel: MediaDetailsViewmodel =koinViewModel(),
    mediaId: Long,
    mediaType: MediaType
) {
    val uiState by viewModel.uiState.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(mediaId, mediaType) {
        viewModel.loadMediaDetails(mediaId, mediaType)
    }

    if (loading) {
        // Your loader
    } else if (error != null) {
        // Your error UI
    } else {
        MediaDetailsContent(
            state = uiState,
            onBack = { /* navController.popBackStack() */ },
            onFavorite = {
                viewModel.onAddMediaToFavouriteListClicked(
                    mediaId = uiState.id.toInt(),
                    favouriteListId = 0
                )
            },
            onAdd = { /* handle add to fav/show sheet */ },
            onPlay = { viewModel.onPlayClicked(uiState.id) },
            onReadMore = { viewModel.onReadMoreDescriptionClicked(uiState.id) },
        )
    }
}

@Composable
fun MediaDetailsContent(
    state: MediaDetailsUiState,
    onBack: () -> Unit = {},
    onFavorite: () -> Unit = {},
    onAdd: () -> Unit = {},
    onPlay: () -> Unit = {},
    onReadMore: () -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(293.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(263.dp)){
                AsyncImage(
                    model = state.backdropUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

            DefaultBar(
                modifier = Modifier.statusBarsPadding(),
                firstOption = painterResource(R.drawable.ic_rounded_star),
                lastOption = painterResource(R.drawable.ic_rounded_add_heart),
            )

                Box(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp)) {
                    Rating(rating = "9.8")
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
                    onClick = onPlay,
                    hasDropShadow = true,
                    dropShadowAlpha = 0.09f,
                    borderWidth = 2,
                    size = 64,
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = state.title,
                style = Theme.textStyle.title.large,
                color = Theme.color.textColors.title,
            )

            Spacer(Modifier.height(12.dp))

            Row() {
                state.genres.forEach { g ->
                    Box(modifier = Modifier.padding(end = 4.dp)) {
                        GenersChip(label = g)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    state.releaseYear,
                    style = Theme.textStyle.label.small,
                    color = Theme.color.textColors.hint
                )
                if (state.mediaDuration.isNotBlank()) {
                    Text(
                        "•",
                        style = Theme.textStyle.label.small,
                        color = Theme.color.textColors.hint
                    )
                    Text(
                        state.mediaDuration,
                        style = Theme.textStyle.label.small,
                        color = Theme.color.textColors.hint
                    )
                }
                if (state.country.isNotBlank()) {
                    Text(
                        "•",
                        style = Theme.textStyle.label.small,
                        color = Theme.color.textColors.hint
                    )
                    Text(
                        state.mediaDuration,
                        style = Theme.textStyle.label.small,
                        color = Theme.color.textColors.hint
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Description",
                color = Theme.color.textColors.title,
                style = Theme.textStyle.title.small,
            )

            // Expandable Description
            var expanded by remember { mutableStateOf(state.isOverviewExpanded) }
            val canExpand = state.overview.length > 160
            val shortDesc = state.overview.take(160)

            ExpandableDescription(
                text = state.overview,
                expanded = state.isOverviewExpanded,
                onToggleExpand =  onReadMore,
                previewColor = Theme.color.textColors.hint,
                suffixColor = Theme.color.primary,
                previewStyle = Theme.textStyle.body.small,
                suffixStyle = Theme.textStyle.label.medium
            )
        }
    }
}

@Composable
fun ExpandableDescription(
    text: String,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    maxPreviewLength: Int = 240,
    previewColor: Color,
    suffixColor: Color,
    previewStyle: TextStyle,
    suffixStyle: TextStyle
) {
    val canExpand = text.length > maxPreviewLength

    val displayText = if (expanded || !canExpand) text else text.take(maxPreviewLength).trimEnd()

    val suffix = when {
        expanded && canExpand -> " Read less"
        !expanded && canExpand -> " Read more"
        else -> ""
    }

    val annotated = buildAnnotatedString {
        append(displayText)
        if (suffix.isNotEmpty()) {
            withStyle(SpanStyle(color = suffixColor,
                fontFamily = suffixStyle.fontFamily,
                fontWeight = suffixStyle.fontWeight,
                fontSize = suffixStyle.fontSize)) {
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

@Preview(showBackground = true)
@Composable
fun PreviewMediaDetailsScreen() {
    MediaDetailsContent(
        state = MediaDetailsUiState(
            id = 1L,
            title = "The Green Mile",
            overview = "In 1935, corrections officer Paul Edgecomb oversees The Green Mile,the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger, and the sadistic\nIn 1935, corrections officer Paul Edgecomb oversees The Green Mile,the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger, and the sadistic",
            mediaType = MediaType.MOVIE,
            rating = 9.7,
            releaseYear = "10-09-2016",
            genres = listOf("comedy", "drama", "action"),
            posterUrl = "",
            backdropUrl = "https://image.tmdb.org/t/p/w780/6A2w0neIqdFJAaXe2wv5hE1GUOa.jpg",
            mediaDuration = "1h 34m",
            country = "US",
            isFavorite = true,
            isOverviewExpanded = false
        )
    )
}