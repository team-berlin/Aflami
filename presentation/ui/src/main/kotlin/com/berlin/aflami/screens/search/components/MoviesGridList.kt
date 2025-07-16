package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.ui.R

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieUIState>,
) {
    Box(modifier = modifier.fillMaxSize()) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Adaptive(minSize = 160.dp)
        ) {
            items(
                count = movies.itemCount,
            ) { index ->
                val movie = movies[index]
                if (movie != null) {
                    MediaCard(
                        modifier = Modifier.height(222.dp),
                        mediaImg = movie.poster,
                        title = movie.title,
                        typeOfMedia = stringResource(R.string.movie),
                        date = movie.releaseYear,
                        rating = movie.rating
                    )
                }
            }
        }
    }
}