package com.berlin.aflami.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun animateColor(
    condition: Boolean,
    trueColor: Color,
    falseColor: Color,
    animationsSpec: AnimationSpec<Color> = tween(300)
): Color {
    return animateColorAsState(
        targetValue = if (condition) trueColor else falseColor,
        animationSpec = animationsSpec
    ).value
}