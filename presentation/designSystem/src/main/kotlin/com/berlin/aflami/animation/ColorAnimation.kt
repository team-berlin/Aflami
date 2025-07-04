package com.berlin.aflami.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

/**
 * Animates between two colors based on a condition.
 *
 * @param isActive If true, [activeColor] is used; otherwise, [inactiveColor] is used.
 * @param activeColor The target color when [isActive] is true.
 * @param inactiveColor The target color when [isActive] is false.
 * @param animationSpec The animation specification to use. Defaults to a 300ms tween.
 * @return The animated color based on the condition.
 */
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