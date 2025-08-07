package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.ui.R

@Composable
fun MovieDetailsMoreLikeThisSection(
    mediaList: List<MovieUiState>,
    onMediaClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        mediaList.forEach { media ->
            MediaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(196.dp),
                mediaImg = media.posterUrl,
                title = media.title,
                typeOfMedia = stringResource(R.string.movie),
                date = media.releaseDate.substringBefore("-"),
                rating = media.rating,
                onClick = {
                    onMediaClick(media.id)
                }

            )
        }
    }
}

@Composable
fun TvShowMoreLikeThisSection(
    mediaList: List<TVShowUiState>,
    onMediaClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        mediaList.forEach { media ->
            MediaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(196.dp),
                mediaImg = media.posterUrl,
                title = media.title,
                typeOfMedia = stringResource(R.string.Tv_Show),
                date = media.releaseDate.substringBefore("-"),
                rating = media.rating,
                onClick =  {
                    onMediaClick(media.id)
                }
            )
        }
    }
}
