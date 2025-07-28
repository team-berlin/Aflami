package com.berlin.aflami.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.screens.search.getMovieGenreIcon
import com.berlin.aflami.screens.search.search.Chips
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.ui.R

@Composable
fun UpcomingMoviesSection(
    movies: List<MovieUIState>,
    genres: List<GenreUiState>,
    onMovieClick: (Long) -> Unit,
    onGenreClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surface)
    ) {
        SectionTitle(
            text = stringResource(R.string.upcoming),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
        )

        GenreChipsRow(
            genres = genres,
            onGenreClick = onGenreClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        )

        MoviesRow(
            movies = movies,
            onMovieClick = onMovieClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = Theme.textStyle.title.medium,
        color = Theme.color.textColors.title,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

@Composable
private fun GenreChipsRow(
    genres: List<GenreUiState>,
    onGenreClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = genres,
            key = { it.id }
        ) { genre ->
            Chips(
                title = genre.name,
                icon = painterResource(getMovieGenreIcon(genre.id)),
                isSelected = genre.isSelected,
                onClick = { onGenreClick(genre.id) }
            )
        }
    }
}

@Composable
private fun MoviesRow(
    movies: List<MovieUIState>,
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MediaCard(
                modifier = modifier
                    .fillParentMaxWidth()
                    .animateItem()
                    .height(222.dp)
                    .padding(bottom = 8.dp),
                mediaImg = movie.poster,
                title = movie.title,
                date = movie.releaseYear,
                rating = movie.rating,
                typeOfMedia = MediaType.MOVIE.name,
                onClick = {
                    onMovieClick(movie.id)
                })
        }
    }
}