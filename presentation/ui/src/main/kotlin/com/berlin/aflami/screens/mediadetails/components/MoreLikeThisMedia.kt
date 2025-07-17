package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.uistate.SimilarMediaUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.ui.R

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
                typeOfMedia = if (mediaType == MediaType.MOVIE) R.string.movie.toString() else R.string.Tv_Show.toString(),
                date = media.releaseYear.substringBefore("-"),
                rating = media.rating
            )
        }
    }
}
@Composable
fun ContentMoreLikeMedia(
    similarMediaState: SimilarMediaUiState,
    mediaType: MediaType,
    ) {
    when (similarMediaState) {

        is SimilarMediaUiState.Loading -> {
            Loading(Modifier)
        }
        is SimilarMediaUiState.Success -> {
            val similarMedia = (similarMediaState).data
            MoreLikeThisScreen(
                mediaList = similarMedia,
                mediaType = mediaType
            )
        }
        is SimilarMediaUiState.Error -> {
            val errorMessage = (similarMediaState).errorMessage
            ErrorMessage(Modifier, errorMessage)



        }

        SimilarMediaUiState.Init ->  Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "LOADING DATA",
                color = Theme.color.textColors.body,
                style = Theme.textStyle.label.large,
                textAlign = TextAlign.Center
            )
        }
    }
}
