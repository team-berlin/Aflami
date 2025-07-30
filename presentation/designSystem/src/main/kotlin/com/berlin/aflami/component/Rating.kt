package com.berlin.aflami.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R


@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: String
) {
    val corner = remember {
        RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 12.dp,
            bottomEnd = 4.dp,
            bottomStart = 12.dp
        )
    }
    Box(modifier = modifier
        .padding(horizontal = 4.dp, vertical = 4.dp)
        .background(Theme.color.primaryVariant, corner)
        .border(1.dp, Theme.color.stroke, corner))
         {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.star),
                contentDescription = stringResource(R.string.card_rate),
                tint = Theme.color.statusColors.yellowAccent
            )

            Text(
                text = rating,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.body
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
fun RatingPreview() {
    AflamiTheme {
        Rating(rating = "9.9")
    }
}