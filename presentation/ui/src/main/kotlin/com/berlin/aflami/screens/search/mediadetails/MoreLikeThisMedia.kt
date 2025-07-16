package com.berlin.aflami.screens.search.mediadetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaUiState

@Composable
fun MoreLikeThisScreen(
    mediaList: List<MediaUiState>,
    mediaType: MediaType
) {
  LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mediaList) { media ->
            MediaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(196.dp),
                mediaImg = media.poster,
                title = media.title,
                typeOfMedia = if (mediaType == MediaType.MOVIE) "Movies" else "Tv Show",
                date = media.releaseYear.substringBefore("-"),
                rating = media.rating
            )
        }
    }
}