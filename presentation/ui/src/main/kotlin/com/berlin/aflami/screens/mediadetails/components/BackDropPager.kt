package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.berlin.aflami.component.CircularIConButton
import com.berlin.aflami.component.Rating
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.utils.formatRating
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.designsystem.R
import kotlinx.coroutines.delay

@Composable
fun BackdropPager(state: MediaDetailsUiState, onPlayClick: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 4 })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % 4
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(293.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height(263.dp)) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                val model = state.backdropUrl
                val painter = rememberAsyncImagePainter(model)
                val imageState by painter.state.collectAsState()
                val contentScale = when (imageState) {
                    is AsyncImagePainter.State.Success, is AsyncImagePainter.State.Loading -> ContentScale.Crop
                    else -> ContentScale.Inside
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = model,
                        contentDescription = null,
                        contentScale = contentScale,
                        modifier = Modifier.fillMaxSize(),
                        error = painterResource(com.berlin.ui.R.drawable.place_holder),
                        fallback = painterResource(com.berlin.ui.R.drawable.place_holder),
                    )
                    if (imageState is AsyncImagePainter.State.Loading) {
                        ShimmerBox(modifier = Modifier.fillMaxSize())
                    }
                }
            }
            Indicator(pagerState = pagerState)
            Box(modifier = Modifier.align(Alignment.BottomStart).padding(4.dp)) {
                Rating(rating = formatRating(state.rating))
            }
        }
        Box(
            Modifier.align(Alignment.BottomCenter).size(72.dp).background(Theme.color.surface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CircularIConButton(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(R.drawable.play_arrow),
                onClick = onPlayClick,
                hasDropShadow = true,
                dropShadowAlpha = 0.09f,
                borderWidth = 2,
                size = 64,
                enabled = state.hasVideo,
                tint = if (state.hasVideo) Theme.color.primary else Theme.color.disable
            )
        }
    }
}