package com.berlin.aflami.onboarding

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

@Composable
fun BottomPageIndicator(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    pageCount: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
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
                    .weight(1f)
                    .size(width = 48.dp, height = 6.dp)
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
@Preview
private fun BottomPageIndicatorPreview() {
    BottomPageIndicator(
        pageNumber = 1,
        pageCount = 3,
    )
}
