package com.berlin.aflami.screens.search.mediadetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.ui.R

@Composable
fun MediaDetailsScreen() {

}

@Composable
fun MediaDetailsContent() {
    Text("Hello Media!")
}

@Preview
@Composable
fun MediaDetailsContentPreview() {
    AflamiTheme {
        MediaDetailsContent()
    }
}

@Composable
fun MediaGallery(modifier: Modifier = Modifier, mediaImages: List<String>) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 160.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(mediaImages.size) { index: Int ->
            AsyncImage(
                model = mediaImages[index],
                contentDescription = stringResource(R.string.media_image),
                modifier = Modifier
                    .width(160.dp)
                    .height(145.dp),
                contentScale = ContentScale.Fit,
//                placeholder = ,
//                error = ,
            )
        }
    }
}