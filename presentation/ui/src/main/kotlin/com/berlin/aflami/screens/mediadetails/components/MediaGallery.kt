package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.uistate.MediaGalleryUiState
import com.berlin.aflami.viewmodel.uistate.SimilarMediaUiState
import com.berlin.ui.R

@Composable
fun MediaGallery(modifier: Modifier = Modifier, mediaImages: List<String>) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 160.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(mediaImages.size) { index: Int ->
            AsyncImage(
                model = mediaImages[index],
                contentDescription = stringResource(R.string.media_image),
                modifier = Modifier
                    .width(160.dp)
                    .height(145.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, color = Theme.color.stroke, RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                 error = painterResource(R.drawable.place_holder),
                placeholder = painterResource(R.drawable.place_holder),
                fallback = painterResource(R.drawable.place_holder),
            )
        }
    }
}
@Composable
fun MediaGalleryContent(
    modifier: Modifier = Modifier,mediaGalleryUiState:MediaGalleryUiState
) {
    when (mediaGalleryUiState) {

        is  MediaGalleryUiState.Loading -> {
            Loading(Modifier)
        }
        is MediaGalleryUiState.Success -> {
            val mediaGallery = (mediaGalleryUiState).data
            MediaGallery(
                mediaImages =mediaGallery
            )
        }
        is MediaGalleryUiState.Error -> {
            val errorMessage = (mediaGalleryUiState).errorMessage
            ErrorMessage(Modifier, errorMessage)

        }

        MediaGalleryUiState.Init ->   Box(
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