package com.berlin.aflami.component

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

class SpeechBubble(
    private val cornerRadius: Dp,
    private val tailWidth: Dp,
    private val tailHeight: Dp,
    private val tailOffsetDp: Dp? = null,
    private val tailOffsetRatio: Float? = null,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }
        val tailWidthPx = with(density) { tailWidth.toPx() }
        val tailHeightPx = with(density) { tailHeight.toPx() }
        val bubbleRectHeight = size.height - tailHeightPx
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(left = 0f, top = 0f, right = size.width, bottom = bubbleRectHeight),
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )
            )
            val tailTipX = calculateTailTipX(size.width, layoutDirection, density) ?: return@apply
            val tailBaseLeft = (tailTipX - tailWidthPx / 2).coerceIn(0f, size.width)
            val tailBaseRight = (tailTipX + tailWidthPx / 2).coerceIn(0f, size.width)
            moveTo(tailBaseLeft, bubbleRectHeight)
            lineTo(tailTipX, size.height)
            lineTo(tailBaseRight, bubbleRectHeight)
            close()
        }
        return Outline.Generic(path)
    }

    private fun calculateTailTipX(
        width: Float,
        layoutDirection: LayoutDirection,
        density: Density
    ): Float? {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        tailOffsetDp?.let {
            val offsetPx = with(density) { it.toPx() }
            return if (isRtl) width - offsetPx else offsetPx
        }
        tailOffsetRatio?.let {
            return width * it
        }
        return null
    }
}