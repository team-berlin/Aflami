package com.berlin.aflami.screens.home

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
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.watchedmedia.WatchedMediaEffect
import com.berlin.aflami.viewmodel.watchedmedia.WatchedMediaInteractionListener
import com.berlin.aflami.viewmodel.watchedmedia.WatchedMediaViewModel
import com.berlin.aflami.viewmodel.watchedmedia.uistate.WatchedMediaUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun WatchedMediaScreen(
    viewModel: WatchedMediaViewModel = koinViewModel(),
    onEffect: (WatchedMediaEffect) -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            onEffect(it)
        }
    }

    WatchedMediaContent(
        state = state,
        listener = viewModel
    )


}


@Composable
fun WatchedMediaContent(
    state: WatchedMediaUiState,
    listener: WatchedMediaInteractionListener
) {

    Column {
        TopBar(
            modifier = Modifier.padding(vertical = 8.dp),
            title = {
                Text(
                    text = "Continue watching",
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title,
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Theme.color.surfaceHigh)
                        .clickable {
                            listener.onBackClicked()
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.arrow_back),
                        tint = Theme.color.textColors.title
                    )
                }
            }
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = state.continueWatchingMedia.size,
                key = { index ->
                    state.continueWatchingMedia[index].id
                }
            ) { index ->
                val watchedMedia = state.continueWatchingMedia[index]
                MediaCard(
                    modifier = Modifier.height(222.dp),
                    mediaImg = watchedMedia.poster,
                    title = watchedMedia.title,
                    onClick = {
                        listener.onMediaCardClick(
                            id = watchedMedia.id,
                            type = watchedMedia.mediaType
                        )
                    },
                    typeOfMedia = watchedMedia.mediaType.name,
                    date = watchedMedia.releaseYear,
                    rating = watchedMedia.rating
                )

            }
        }

    }

}
