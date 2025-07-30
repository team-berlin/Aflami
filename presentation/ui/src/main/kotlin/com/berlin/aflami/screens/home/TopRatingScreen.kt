package com.berlin.aflami.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.ui.color.ExtraColors.BackgroundGradient
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.toprating.TopRatingScreenEffect
import com.berlin.aflami.viewmodel.home.toprating.TopRatingViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R
import com.example.navigation.Destination
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopRatingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    topRatingViewModel: TopRatingViewModel = koinViewModel(),
) {
    val screenState by topRatingViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        topRatingViewModel.effect.collect { effect ->
            when (effect) {
                is TopRatingScreenEffect.NavigateToMediaDetailsScreen -> {
                    navController.navigate(
                        Destination.MediaDetailsScreen.route(
                            effect.id, effect.type.name
                        )
                    )
                }

                is TopRatingScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = screenState.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }

    val topRatedItems = topRatingViewModel.topRatedPagingFlow.collectAsLazyPagingItems()
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !screenState.isLoading
    ) {
        TopWatchingContent(
            topRatedMediaItems = topRatedItems, viewModel = topRatingViewModel
        )
    }


}

@Composable
private fun TopWatchingContent(
    topRatedMediaItems: LazyPagingItems<MediaUiState>,
    viewModel: TopRatingViewModel,
) {
    val listState = rememberLazyGridState()
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(BackgroundGradient)
        ) {
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(R.drawable.top_rate_icons),
                contentDescription = null,

                )

            LazyVerticalGrid(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp),
                columns = GridCells.Adaptive(minSize = 160.dp),
                contentPadding = PaddingValues(
                    start = 16.dp, end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = topRatedMediaItems.itemCount) { index ->
                    val topRatedMedia = topRatedMediaItems[index]
                    if (topRatedMedia != null) {
                        MediaCard(
                            modifier = Modifier.height(222.dp),
                            mediaImg = topRatedMedia.poster,
                            title = topRatedMedia.title,
                            onClick = {
                                viewModel.onMediaCardClicked(
                                    id = topRatedMedia.id, mediaType = topRatedMedia.mediaType
                                )
                            },
                            typeOfMedia = topRatedMedia.mediaType.name,
                            date = topRatedMedia.releaseYear,
                            rating = topRatedMedia.rating
                        )
                    }
                }
            }
        }

        DefaultBar(
            modifier = Modifier.statusBarsPadding(),
            onNavigateBackClicked = {  viewModel.onBackClicked() },
            optionContainerColor = Theme.color.surfaceHigh,
            containerColor = appBarBgColor,
            title = stringResource(R.string.top_rating)
        )

    }
}