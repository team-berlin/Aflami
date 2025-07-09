package com.berlin.aflami.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    mediaImg: String,
    title: String,
    typeOfMedia: String,
    date: String,
    rating: Double
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Theme.color.stroke, RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = mediaImg,
            contentDescription = stringResource(R.string.api_image_card_content),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF0D090B)
                        )
                    )
                ),
        )

        Rating(modifier = Modifier.align(Alignment.TopEnd), rating)

        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.label.large,
                color = Theme.color.textColors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = typeOfMedia,
                    style = Theme.textStyle.label.small,
                    color = Theme.color.textColors.onPrimaryBody
                )

                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(
                            Theme.color.textColors.onPrimaryBody,
                            RoundedCornerShape(3.dp)
                        )
                )

                Text(
                    text = date,
                    style = Theme.textStyle.label.small,
                    color = Theme.color.textColors.onPrimaryBody
                )
            }
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun MediaCardPreview1() {
    AflamiTheme {
        MediaCard(
            modifier = Modifier.size(width = 156.dp, height = 222.dp),
            "https://i.pinimg.com/736x/2d/4c/77/2d4c7718ccdf3d714654dbd3d66da00f.jpg",
            "Your Name",
            "TV show",
            "2016",
            rating = 9.9
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun MediaCardPreview2() {
    AflamiTheme {
        MediaCard(
            modifier = Modifier.size(width = 328.dp, height = 196.dp),
            "https://i.pinimg.com/736x/2d/4c/77/2d4c7718ccdf3d714654dbd3d66da00f.jpg",
            "Grave of the Fireflies",
            "TV show",
            "2016",
            rating = 9.9
        )
    }
}
