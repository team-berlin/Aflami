package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

val PagerState.pageOffset: Float
    get() = currentPage + currentPageOffsetFraction

@Composable
fun BoxScope.Indicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    circleSpacing: Dp = 2.dp,
    dotWidth: Dp = 4.dp,
    dotHeight: Dp = 4.dp,
    dotColor: Color = Theme.color.textColors.hint,
    activeLineHeight: Dp = 24.dp,
    activeLineColor: Color = Theme.color.primary,
    radius: CornerRadius = with(LocalDensity.current) { CornerRadius(4.dp.toPx(), 4.dp.toPx()) },
    backGroundColor: Color = Theme.color.primaryVariant,
) {
    val height = with(LocalDensity.current) {
        ((pagerState.pageCount - 1) * (dotHeight.toPx() + circleSpacing.toPx()) + activeLineHeight.toPx() + 8.dp.toPx()).toDp()
    }

    Canvas(
        modifier = modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .width(8.dp)
            .height(height)
            .clip(shape = RoundedCornerShape(4.dp)),
    ) {

        val spacing = circleSpacing.toPx()
        val dotWidthPx = dotWidth.toPx()
        val dotHeightPx = dotHeight.toPx()
        val activeDotHeightPx = activeLineHeight.toPx()
        val totalDotsHeight = (pagerState.pageCount - 1) * dotHeightPx + (pagerState.pageCount - 1) * spacing + activeDotHeightPx
        var y = (size.height + totalDotsHeight) / 2
        val x = center.x

        drawRoundRect(
            color = backGroundColor,
            size = size,
        )

        repeat(
            pagerState.pageCount
        ) { i ->
            val posOffset = pagerState.pageOffset
            val dotOffset = posOffset % 1
            val current = posOffset.toInt()

            val factor = (dotOffset * (activeDotHeightPx - dotHeightPx))

            val calculatedHeight = when {
                i == current -> activeDotHeightPx - factor
                i - 1 == current || (i == 0 && posOffset > pagerState.pageCount - 1) -> dotHeightPx + factor
                else -> dotHeightPx
            }
            val indicatorColor =
                if (i == current) activeLineColor else dotColor
            drawIndicator(
                x = x,
                y = y - calculatedHeight / 2,
                width = dotWidthPx,
                height = calculatedHeight,
                radius = radius,
                color = indicatorColor
            )
            y -= calculatedHeight + spacing
        }
    }
}

private fun DrawScope.drawIndicator(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: CornerRadius,
    color: Color
) {
    val rect = RoundRect(
        x - width / 2,
        y - height / 2,
        x + width / 2,
        y + height / 2,
        radius
    )
    val path = Path().apply { addRoundRect(rect) }
    drawPath(path = path, color = color)
}