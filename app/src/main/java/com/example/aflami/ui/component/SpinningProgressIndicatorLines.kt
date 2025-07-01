package com.example.aflami.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SpinningProgressIndicatorLines(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    lineLength: Dp = 6.dp,
    lineWidth: Dp = 1.5.dp,
    color: Color = Color.Companion.White,
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinning")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            )
        ),
        label = "progress"
    )
    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = Offset(size.toPx() / 2, size.toPx() / 2)
        val radius = (size.toPx() - lineLength.toPx()) / 2
        val lines = 8

        for (i in 0 until lines) {
            val angle = (i * 360f / lines) * (PI / 180f)

            val currentActiveIndex = (animationProgress * lines).toInt() % lines
            val distanceFromActive = minOf(
                abs(i - currentActiveIndex),
                abs(i - currentActiveIndex + lines),
                abs(i - currentActiveIndex - lines)
            )

            val alpha = when (distanceFromActive) {
                0 -> 1f
                1 -> 0.7f
                2 -> 0.4f
                3 -> 0.2f
                else -> 0.1f
            }

            val startX = center.x + cos(angle) * radius
            val startY = center.y + sin(angle) * radius
            val endX = center.x + cos(angle) * (radius + lineLength.toPx())
            val endY = center.y + sin(angle) * (radius + lineLength.toPx())

            drawLine(
                color = color.copy(alpha = alpha),
                start = Offset(startX.toFloat(), startY.toFloat()),
                end = Offset(endX.toFloat(), endY.toFloat()),
                strokeWidth = lineWidth.toPx(),
                cap = StrokeCap.Companion.Round
            )
        }
    }
}