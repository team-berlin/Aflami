package com.berlin.aflami.screens.onBoarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

@Composable
fun Indicator(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    pageCount: Int,
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val spacing = 4.dp
    val totalSpacing = spacing * (pageCount - 1)
    val availableWidth = screenWidth - totalSpacing
    val indicatorWidth: Dp = (availableWidth / pageCount)


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        repeat(pageCount) { page ->
            val targetColor = when {
                page == pageNumber -> Theme.color.textColors.onPrimary
                page < pageNumber -> Theme.color.textColors.onPrimary
                else -> Theme.color.textColors.onPrimaryHint
            }

            val animatedColor by animateColorAsState(
                targetValue = targetColor,
                label = "indicatorColor"
            )

            Box(
                modifier = Modifier
                    .size(width = indicatorWidth, height = 6.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(color = animatedColor)
                    .border(
                        1.dp,
                        Theme.color.stroke,
                        RoundedCornerShape(100.dp)
                    )
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun IndicatorPreview() {
    Indicator(
        pageNumber = 5,
        pageCount = 15,
    )
}
