package com.example.aflami.designsystem.theme.color

import androidx.compose.ui.graphics.Color

data class AflamiColors(
    val primary: Color,
    val secondary: Color,
    val primaryVariant: Color,
    val gradientColors:GradientColors,
    val textColors: TextColors,
    val statusColors: StatusColors,
)

data class GradientColors(
    val overly: List<Color>,
    val streakGradient: List<Color>,
    val pointsOverly: List<Color>,
)
data class TextColors(
    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val onPrimary: Color,
    val onPrimaryBody: Color,
    val onPrimaryHint: Color,
    val disable: Color,
    val iconBackground: Color,
    val blurOverlay: Color,
    val onPrimaryButton :Color,
    )

data class StatusColors(
    val redAccent: Color,
    val redVariant: Color,
    val yellowAccent: Color,
    val greenAccent: Color,
    val greenVariant: Color,
    val darkBlue: Color,
    val blueAccent: Color,
    val blueCard: Color,
    val navyCard: Color,
    val yellowCard: Color,
    val backgroundCircles: Color,
    val profileOverly: Color
)