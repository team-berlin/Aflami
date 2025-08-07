package com.berlin.aflami.screens.listdetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.berlin.aflami.component.IconButton
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.ui.R

@Composable
fun MoviesListItem(
    movies: LazyPagingItems<MovieUiState>,
    listId: Int,
    modifier: Modifier = Modifier,
    onClickMovie: (Long) -> Unit,
    onClickDislikeItem: (Int, Long) -> Unit,
) {
    val itemState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        state = itemState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = movies.itemCount, key = movies.itemKey { it.id }) { index ->
            val movie = movies[index] ?: return@items
            Box {
                MediaCard(
                    mediaImg = movie.posterUrl,
                    title = movie.title,
                    date = movie.releaseDate,
                    rating = movie.rating,
                    typeOfMedia = stringResource(R.string.movie),
                    onClick = { onClickMovie(movie.id) })

                IconButton(
                    modifier = modifier
                        .padding(start = 4.dp, top = 4.dp)
                        .size(32.dp),
                    paddingValues = PaddingValues(6.dp),
                    painter = painterResource(R.drawable.heart),
                    contentDescription = null,
                    onClick = { onClickDislikeItem(listId, movie.id) },
                )
            }
        }
    }
}
