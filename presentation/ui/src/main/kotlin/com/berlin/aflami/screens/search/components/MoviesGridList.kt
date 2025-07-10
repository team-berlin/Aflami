package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.ui.R

@Composable
fun MoviesList(
    movies: List<MovieUIState>,
    onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MediaCard(
                modifier = Modifier
                    .height(222.dp)
                    .clickable { onMovieClick(movie.id.toInt()) },
                mediaImg = movie.poster,
                title = movie.title,
                typeOfMedia = stringResource(R.string.movie),
                date = movie.releaseYear,
                rating = movie.rating
            )
        }
    }
}