package com.berlin.aflami.screens.lists.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun ListCard(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 160.dp, height = 147.dp)


    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.90f)
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
        )
        {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 20.dp)
                    .align(Alignment.BottomStart)
                    .background(Theme.color.surfaceHigh),

                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    title,
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.medium
                )
                Text(
                    text = stringResource(R.string.item_count, count),
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
                .align(Alignment.TopStart)
        )
    }

}

@ThemeAndLocalePreviews
@Composable
private fun ListCardPreview() {
    AflamiTheme {
        ListCard(
            title = "My favorite",
            count = 18
        )
    }
}