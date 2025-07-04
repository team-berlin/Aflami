package com.berlin.aflami.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun animatedConditionalColor(
    isActive: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    animationSpec: AnimationSpec<Color> = tween(durationMillis = 300)
): Color {
    val targetColor = if (isActive) activeColor else inactiveColor
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = animationSpec,
        label = "animatedConditionalColor"
    )
    return animatedColor
}