package com.berlin.aflami.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    mediaImg: Any,
    title: String,
    description: String,
    date: String,
    rating: Double
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Theme.color.stroke, RoundedCornerShape(16.dp))

    ) {
        AsyncImage(
            modifier = modifier,
            model = mediaImg,
            contentDescription = "api image card",
            contentScale = ContentScale.Crop
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
                color = Theme.color.textColors.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = description,
                    style = Theme.textStyle.label.large,
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
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.onPrimaryBody
                )

            }

        }

    }


}

@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: Double
) {

    val corner = remember {
        RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 12.dp,
            bottomEnd = 4.dp,
            bottomStart = 12.dp
        )
    }
    Row(
        modifier = modifier
            .padding(end = 4.dp, top = 4.dp)
            .size(width = 50.dp, height = 28.dp)
            .border(1.dp, Theme.color.stroke, corner)
            .background(Theme.color.primaryVariant, corner)
            .padding(vertical = 6.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.star),
                contentDescription = "card rate",
                tint = Theme.color.statusColors.yellowAccent
            )

            Text(
                text = rating.toString(),
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.body
            )
        }


    }
}

@ThemeAndLocalePreviews
@Composable
fun Preview1() {
    MediaCard(
        modifier = Modifier.size(width = 156.dp, height = 222.dp),
        R.drawable.api_img,
        "title",
        "description",
        "2016",
        rating = 9.9
    )
}

@ThemeAndLocalePreviews
@Composable
fun Preview2() {
    MediaCard(
        modifier = Modifier.size(width = 328.dp, height = 196.dp),
        R.drawable.api_img2,
        "title",
        "description",
        "2016",
        rating = 9.9
    )
}
@ThemeAndLocalePreviews
@Composable
fun Preview3() {
    AflamiTheme(isDarkTheme = true) {
        MediaCard(
            modifier = Modifier.size(width = 156.dp, height = 222.dp),
            R.drawable.api_img,
            "title",
            "description",
            "2016",
            rating = 9.9
        )
    }
}

@ThemeAndLocalePreviews
@Composable
fun Preview4() {
    AflamiTheme(isDarkTheme = true) {
        MediaCard(
            modifier = Modifier.size(width = 328.dp, height = 196.dp),
            R.drawable.api_img2,
            "title",
            "description",
            "2016",
            rating = 9.9
        )
    }
}







