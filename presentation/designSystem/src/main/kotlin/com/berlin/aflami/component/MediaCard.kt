package com.berlin.aflami.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.berlin.aflami.ui.color.ExtraColors
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R
import com.berlin.safeimageviewer.SafeImageViewer

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    mediaImg: String,
    title: String,
    typeOfMedia: String,
    date: String,
    rating: String,
    onClick: (() -> Unit)? = null
) {
    val painter = rememberAsyncImagePainter(mediaImg)
    val imageState by painter.state.collectAsState()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Theme.color.stroke, RoundedCornerShape(16.dp))
            .clickable {
                onClick?.invoke()
            }
    ) {
        val contentScale = when (imageState) {
            is AsyncImagePainter.State.Success, is AsyncImagePainter.State.Loading -> ContentScale.Crop
            else -> ContentScale.Inside
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AsyncImage(
                model = mediaImg,
                contentDescription = null,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize(),
                error = painterResource(R.drawable.place_holder),
                fallback = painterResource(R.drawable.place_holder),
            )
            if (imageState is AsyncImagePainter.State.Loading) {
                ShimmerBox(modifier = Modifier.fillMaxSize())
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .background(ExtraColors.overlayGradient)
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

                    text = if (typeOfMedia == "TV_SHOW") stringResource(R.string.tv_show) else stringResource(
                        R.string.movie
                    ),
                    style = Theme.textStyle.label.small,
                    color = Theme.color.textColors.onPrimaryBody,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
                    color = Theme.color.textColors.onPrimaryBody,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

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
            rating = "9.9"
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
            rating = "9.9"
        )
    }
}