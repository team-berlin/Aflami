package com.berlin.aflami.screens.search.mediadetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.example.navigation.Destination

@Composable
fun MoreLikeThisScreen(
    mediaList: List<MediaUiState>,
    mediaType: MediaType
) {
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mediaList) { media ->
            MediaCard(
                modifier = Modifier.size(width = 160.dp, height = 222.dp),
                mediaImg = media.poster,
                title = media.title,
                typeOfMedia = if (mediaType == MediaType.MOVIE) "Movies" else "Tv Show",
                date = media.releaseYear.substringBefore("-"),
                rating = media.rating
            )
        }
    }
}