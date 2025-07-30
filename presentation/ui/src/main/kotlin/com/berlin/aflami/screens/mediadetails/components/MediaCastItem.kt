package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R
import com.berlin.safeimageviewer.SafeImageViewer

@Composable
fun MediaCastItem(
    modifier: Modifier = Modifier,
    name: String,
    poster: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Box(
            modifier = modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, color = Theme.color.stroke,
                    RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ){
            SafeImageViewer(
                imageUri = poster,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.place_holder),
                error = painterResource(R.drawable.place_holder),
                fallback = painterResource(R.drawable.place_holder),
            )
        }

        Text(
            text = name,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(74.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun MediaCastPreview() {
    MediaCastItem(
        name = "Tom Hanks",
        poster = "https://i.pinimg.com/736x/2d/4c/77/2d4c7718ccdf3d714654dbd3d66da00f.jpg",

        )

}