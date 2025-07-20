package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun GallerySection(
    modifier: Modifier = Modifier,
    mediaImages: List<String>,
    columns: Int = 2 // You can set as many columns as you like
) {
    val rows = (mediaImages.size + columns - 1) / columns // Int ceil

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < mediaImages.size) {
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
                    } else {
                        // Fills empty space to align with other rows if last row isn't full
                        Spacer(Modifier.width(160.dp).height(145.dp))
                    }
                }
            }
        }
    }
}