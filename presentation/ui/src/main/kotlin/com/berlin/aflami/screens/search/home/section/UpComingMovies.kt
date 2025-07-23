package com.berlin.aflami.screens.search.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.screens.search.screen.Chips
import com.berlin.aflami.screens.search.screen.genreMapper
import com.berlin.aflami.screens.search.screen.getGenreIcon
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search.GenreType
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState


fun LazyListScope.upcomingMovies(
    movies: List<MovieUIState>,
    onMovieClicked: (movieId: Long) -> Unit,
    moviesGenres: List<GenreUiState>,
    onChangeMovieGenre: (genreType: GenreType) -> Unit,
    modifier: Modifier = Modifier,
) {
    stickyHeader {
        Column {
            Text(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .background(Theme.color.surface)
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                text = "Upcoming",
                style = Theme.textStyle.title.medium,
                color = Theme.color.textColors.title,
                textAlign = TextAlign.Start,
            )

            LazyRow(
                modifier = Modifier
                    .height(96.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = moviesGenres
                ) { genre ->
                    val genreItem = genre.genres.type
                    Chips(
                        title = stringResource(genreMapper(genreItem)),
                        icon = painterResource(getGenreIcon(genreItem)),
                        isSelected = genre.genres.isSelected,
                        onClick = { onChangeMovieGenre(genreItem) })
                }
            }
        }
    }

    items(
        items = movies, key = { it.id }) { movie ->
        with(movie) {
            MediaCard(
                modifier = modifier
                    .fillMaxWidth()
                    .height(222.dp)
                    .padding(bottom = 8.dp),
                mediaImg = movie.poster,
                title = movie.title,
                date = movie.releaseYear,
                rating = movie.rating,
                typeOfMedia = "Movies",
                onClick = {
                    onMovieClicked(movie.id)
                })
        }
    }
}