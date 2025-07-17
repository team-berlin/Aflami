package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun MediaCastItem(
    modifier: Modifier = Modifier,
    name: String,
    poster: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        AsyncImage(
            error = painterResource(R.drawable.place_holder),
            placeholder = painterResource(R.drawable.place_holder),
            fallback = painterResource(R.drawable.place_holder),
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, color = Theme.color.stroke, RoundedCornerShape(16.dp)),
            model = poster,
            contentDescription = stringResource(R.string.cast_image),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

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