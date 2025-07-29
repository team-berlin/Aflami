package com.berlin.aflami.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.toprating.TopRatingScreenEffect
import com.berlin.aflami.viewmodel.home.toprating.TopRatingUiState
import com.berlin.aflami.viewmodel.home.toprating.TopRatingViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopRatingScreen(
    modifier: Modifier = Modifier,
    onEffect: (TopRatingScreenEffect) -> Unit,
    topRatingViewModel: TopRatingViewModel = koinViewModel(),
) {
    val screenState by topRatingViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        topRatingViewModel.effect.collect {
            onEffect(it)
        }
    }
    AnimatedVisibility(screenState.isLoading) {
        Loading()
    }

    val topRatedItems = topRatingViewModel.topRatedPagingFlow.collectAsLazyPagingItems()
    AnimatedVisibility(!screenState.isLoading) {
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
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(modifier = Modifier.padding(vertical = 8.dp), title = {
            Text(
                text = stringResource(R.string.top_rating),
                style = Theme.textStyle.title.large,
                color = Theme.color.textColors.title,
            )
        }, leadingIcon = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Theme.color.surfaceHigh)
                    .clickable {
                        viewModel.onBackClicked()
                    }
                    .padding(10.dp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.arrow_back),
                    tint = Theme.color.textColors.title
                )
            }
        })
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 8.dp
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
}