package com.berlin.aflami.screens.onBoarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

@Composable
fun Indicator(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    pageCount: Int,
    spacing: Dp = 4.dp,
    isFullWidth: Boolean = true
) {
    BoxWithConstraints(modifier = modifier) {
        val availableWidth = if (isFullWidth)
            this.maxWidth - (spacing * (pageCount - 1))
        else this.maxWidth * 0.5f - (spacing * (pageCount - 1))
        val indicatorWidth = availableWidth / pageCount

        Row(
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
                        .background(animatedColor)
                        .border(
                            1.dp,
                            Theme.color.stroke,
                            RoundedCornerShape(100.dp)
                        )
                )
            }
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
