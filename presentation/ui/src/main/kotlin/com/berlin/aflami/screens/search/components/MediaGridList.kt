package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R

@Composable
fun MediaGridList(
    modifier: Modifier = Modifier,
    media: LazyPagingItems<MediaUiState>,
    onMovieClick: (Long, mediaType: MediaType) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = media.itemCount,
            ) { index ->
                val movie = media[index]
                if (movie != null) {
                    MediaCard(
                        modifier = Modifier
                            .height(222.dp),
                        onClick = {
                            onMovieClick(
                                movie.id, movie.mediaType ?: MediaType.MOVIE
                            )
                        },
                        mediaImg = movie.poster,
                        title = movie.title,
                        typeOfMedia = if (movie.mediaType == MediaType.MOVIE) stringResource(R.string.movie) else stringResource(
                            com.berlin.designsystem.R.string.tv_shows
                        ),
                        date = movie.releaseYear,
                        rating = movie.rating
                    )
                }
            }
        }
    }
}