package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.berlin.aflami.component.CircularIconButton
import com.berlin.aflami.component.Rating
import com.berlin.aflami.component.ShimmerBox
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsUiState
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsUiState
import com.berlin.aflami.viewmodel.util.toEnglishDigits
import com.berlin.designsystem.R
import kotlinx.coroutines.delay

@Composable
fun MovieBackdropPager(state: MovieDetailsUiState, onPlayClick: () -> Unit) {
    val posterList = state.posters.take(4)
    val pagerState = rememberPagerState(pageCount = { posterList.size })

    LaunchedEffect(pagerState) {
        while (posterList.size > 1) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % posterList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(293.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(263.dp)
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                val model = posterList[page]
                val painter = rememberAsyncImagePainter(model)
                val imageState by painter.state.collectAsState()

                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model= model,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        error = painterResource(R.drawable.place_holder),
                        fallback = painterResource(R.drawable.place_holder),
                    )

                    val statusBarTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                    val extra = 32.dp
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(statusBarTop + extra)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Theme.color.surface,
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    if (imageState is AsyncImagePainter.State.Loading) {
                        ShimmerBox(modifier = Modifier.fillMaxSize())
                    }
                }
            }

            Indicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.BottomEnd)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp)
            ) {
                Rating(rating = state.movieUiState.rating.toEnglishDigits())
            }
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .size(72.dp)
                .background(Theme.color.surface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CircularIconButton(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(R.drawable.play_arrow),
                onClick = onPlayClick,
                hasDropShadow = true,
                dropShadowAlpha = 0.09f,
                borderWidth = 2,
                size = 64,
                enabled = state.isMovieHasVideo,
                tint = if (state.isMovieHasVideo) Theme.color.primary else Theme.color.disable
            )
        }
    }
}
@Composable
fun TVShowBackdropPager(state: TVShowDetailsUiState, onPlayClick: () -> Unit) {
    val posterList = state.posters.take(4)
    val pagerState = rememberPagerState(pageCount = { posterList.size })

    LaunchedEffect(pagerState) {
        while (posterList.size > 1) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % posterList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(293.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(263.dp)
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                val model = posterList[page]
                val painter = rememberAsyncImagePainter(model)
                val imageState by painter.state.collectAsState()

                val contentScale = when (imageState) {
                    is AsyncImagePainter.State.Success,
                    is AsyncImagePainter.State.Loading -> ContentScale.Crop
                    else -> ContentScale.Inside
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model= model,
                        contentDescription = null,
                        contentScale = contentScale,
                        modifier = Modifier.fillMaxSize(),
                        error = painterResource(R.drawable.place_holder),
                        fallback = painterResource(R.drawable.place_holder),
                    )

                    val statusBarTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                    val extra = 32.dp
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(statusBarTop + extra)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Theme.color.surface,
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    if (imageState is AsyncImagePainter.State.Loading) {
                        ShimmerBox(modifier = Modifier.fillMaxSize())
                    }
                }
            }

            Indicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.BottomEnd)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp)
            ) {
                Rating(rating = state.tvShowUiState.rating.toEnglishDigits())
            }
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .size(72.dp)
                .background(Theme.color.surface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CircularIconButton(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(R.drawable.play_arrow),
                onClick = onPlayClick,
                hasDropShadow = true,
                dropShadowAlpha = 0.09f,
                borderWidth = 2,
                size = 64,
                enabled = state.isTVShowHasVideo,
                tint = if (state.isTVShowHasVideo) Theme.color.primary else Theme.color.disable
            )
        }
    }
}