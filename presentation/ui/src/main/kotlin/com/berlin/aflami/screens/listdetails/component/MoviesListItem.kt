package com.berlin.aflami.screens.listdetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.shareduistate.MediaType
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
                Box(
                    Modifier
                        .align(Alignment.TopStart)
                        .size(32.dp)
                        .padding(start = 4.dp, top = 4.dp)
                        .background(
                            color = Theme.color.iconBackground,
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            onClickDislikeItem(listId, movie.id)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.heart),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Theme.color.statusColors.redAccent
                    )
                }
                MediaCard(
                    modifier = modifier.height(222.dp),
                    mediaImg = movie.posterUrl,
                    title = movie.title,
                    date = movie.releaseDate,
                    rating = movie.rating,
                    typeOfMedia = MediaType.MOVIE.name,
                    onClick = { onClickMovie(movie.id) },
                )
            }
        }
    }
}
