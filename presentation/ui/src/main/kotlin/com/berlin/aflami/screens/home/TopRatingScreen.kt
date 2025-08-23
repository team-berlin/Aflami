package com.berlin.aflami.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.ui.color.ExtraColors.BackgroundGradient
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.toprating.TopRatingScreenEffect
import com.berlin.aflami.viewmodel.home.toprating.TopRatingViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.util.toEnglishDigits
import com.berlin.ui.R

@Composable
fun TopRatingScreen(
    topRatingViewModel: TopRatingViewModel = hiltViewModel(),
) {
    val topRatingScreenState by topRatingViewModel.state.collectAsStateWithLifecycle()
    val navController = Theme.navController

    LaunchedEffect(Unit) {
        topRatingViewModel.effect.collect { effect ->
            when (effect) {
                is TopRatingScreenEffect.NavigateToMediaDetailsScreen -> {
                    when (effect.mediaType) {
                        MediaType.MOVIE -> navController.navigate(
                            MovieDetailsDestination(
                                effect.mediaId
                            )
                        )

                        MediaType.TV_SHOW -> navController.navigate(
                            TVShowDetailsDestination(
                                effect.mediaId
                            )
                        )
                    }
                }

                is TopRatingScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None, visible = topRatingScreenState.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(), text = stringResource(R.string.loading)
        )
    }

    val topRatedItems = topRatingScreenState.topRatedMediaFlow.collectAsLazyPagingItems()
    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None, visible = !topRatingScreenState.isLoading
    ) {
        TopRatingContent(
            topRatedMediaItems = topRatedItems, viewModel = topRatingViewModel
        )
    }


}

@Composable
private fun TopRatingContent(
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
            .background(Theme.color.surface)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(brush = BackgroundGradient),
        ) {}
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .blur(2.dp),
            painter = painterResource(R.drawable.fires),
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 160.dp)
                .size(200.dp)
                .blur(3.dp),
            painter = painterResource(R.drawable.fire),
            contentDescription = null,
        )

        LazyVerticalGrid(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
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
                                mediaId = topRatedMedia.id,
                                mediaType = topRatedMedia.mediaType ?: MediaType.MOVIE
                            )
                        },
                        typeOfMedia = topRatedMedia.mediaType.name,
                        date = topRatedMedia.releaseYear,
                        rating = topRatedMedia.rating.toEnglishDigits()
                    )
                }
            }
        }
        DefaultBar(
            modifier = Modifier
                .background(appBarBgColor)
                .statusBarsPadding(),
            onNavigateBackClicked = { viewModel.onBackClicked() },
            optionContainerColor = Theme.color.surfaceHigh,
            containerColor = Color.Unspecified,
            title = stringResource(R.string.top_rating),

            )
    }



}