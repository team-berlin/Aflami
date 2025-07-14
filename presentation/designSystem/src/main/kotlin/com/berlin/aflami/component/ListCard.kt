package com.berlin.aflami.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme


@Composable
fun ListCard(
    title: String,
    itemCount: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 160.dp, height = 147.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width = 160.dp, height = 135.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 24.dp,
                        bottomStart = 24.dp,
                        topEnd = 16.dp,
                        topStart = 16.dp
                    )
                )
                .background(Theme.color.surfaceHigh)
                .align(Alignment.BottomEnd)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterStart)
                    .offset(y = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    title,
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.medium
                )
                Text(
                    itemCount,
                    color = Theme.color.textColors.hint,
                    style = Theme.textStyle.label.large
                )
            }

        }

        Box(
            modifier = Modifier
                .size(width = 80.dp, height = 33.dp)
                .clip(
                    RoundedCornerShape(
                        topEnd = 16.dp,
                        topStart = 24.dp
                    )
                )
                .background(Theme.color.surfaceHigh)
        )
    }

}

@ThemeAndLocalePreviews
@Composable
private fun ListCardPreview() {
    AflamiTheme {
        ListCard(
            title = "My favorite",
            itemCount = "12 item"
        )
    }
}