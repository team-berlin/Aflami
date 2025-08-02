package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R

@Composable
fun MoreLikeThisSection(
    mediaList: List<MediaUiState>,
    mediaType: MediaType,
    onMediaClick: (Long, MediaType) -> Unit
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
                    .height(196.dp)
                    .clickable { onMediaClick(media.id,mediaType) },
                mediaImg = media.poster,
                title = media.title,
                typeOfMedia = if (mediaType == MediaType.MOVIE) stringResource( R.string.movie) else stringResource( R.string.Tv_Show),
                date = media.releaseYear.substringBefore("-"),
                rating = media.rating
            )
        }
    }
}
