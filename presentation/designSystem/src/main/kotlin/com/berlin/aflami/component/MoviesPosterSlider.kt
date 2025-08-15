package com.berlin.aflami.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.color.ExtraColors.black50
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R


@Composable
fun RatingCard(
    modifier: Modifier, rating: String
) {
    Row(
        modifier = modifier
            .padding(top = 4.dp, end = 5.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 4.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 4.dp
                )
            )
            .background(
                Theme.color.primaryVariant
            )
            .border(
                width = 1.dp, color = Theme.color.stroke, shape = RoundedCornerShape(
                    topStart = 4.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 4.dp
                )
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(R.drawable.ic_rating),
            tint = Theme.color.statusColors.yellowAccent,
            contentDescription = "rating"
        )
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = rating,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.body
        )
    }
}

@Composable
fun PlayButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(
                color = Theme.color.textColors.onPrimary.copy(alpha = .87f)
            )
            .border(1.dp, color = Theme.color.stroke)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(R.drawable.play_arrow),
            contentDescription = "Featured",
            tint = Theme.color.primary,
        )
    }
}

@Composable
fun BlurredPosterBackground(
    imageUrl: String, modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Blurred Poster Background",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .background(black50).blur(16.dp),
    )
}
