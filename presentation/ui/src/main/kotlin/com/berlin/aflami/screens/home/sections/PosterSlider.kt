package com.berlin.aflami.screens.home.sections

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.component.PlayButton
import com.berlin.aflami.component.RatingCard
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.safeimageviewer.SafeImageViewer
import kotlinx.coroutines.delay

@Composable
fun PosterSlider(
    modifier: Modifier = Modifier,
    mediaList: List<MediaUiState>,
    onMovieItemClicked: (movieId: Long) -> Unit = {},
    onTVShowItemClicked: (tvShowId: Long) -> Unit = {},
    pagerState: PagerState,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = remember { 244.dp }
    val contentPadding = remember { (screenWidth - itemWidth) / 2 }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            if (pagerState.pageCount > 0) {
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(
                    nextPage

                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(itemWidth),
        contentPadding = PaddingValues(horizontal = contentPadding),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) { pageIndex ->
        val actualIndex = pageIndex % mediaList.size
        val mediaItem = mediaList.getOrNull(actualIndex)
        mediaItem?.let {
            SliderCard(
                isCentered = pageIndex == pagerState.currentPage,
                onClick = {
                    when (it.mediaType) {
                        MediaType.MOVIE -> onMovieItemClicked(it.id)
                        MediaType.TV_SHOW -> onTVShowItemClicked(it.id)
                        else -> throw IllegalArgumentException("Unknown media type")
                    }
                },
                rating = it.rating,
                posterImageUrl = it.poster
            )
        }
    }
}


@Composable
fun SliderCard(
    isCentered: Boolean = false,
    onClick: () -> Unit,
    rating: String,
    posterImageUrl: String,
) {

    val cardWidth = animateDpAsState(
        targetValue = if (isCentered) 244.dp else 207.dp,
    ).value
    val cardHeight = animateDpAsState(
        targetValue = if (isCentered) 300.dp else 276.dp,
    ).value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = { onClick() },
                interactionSource = null,
                indication = null,

                ),
        contentAlignment = Alignment.BottomCenter
    ) {

        AsyncImage(
            model = posterImageUrl,
            contentDescription = "Poster Image",
            modifier = Modifier
                .width(cardWidth)
                .height(cardHeight)
                .clip(RoundedCornerShape(24.dp)),
        )
        if (isCentered) {
            RatingCard(
                modifier = Modifier.align(Alignment.TopEnd),
                rating = rating,
            )
            PlayButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = { })
        }
    }
}

