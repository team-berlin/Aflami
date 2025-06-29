package com.example.aflami.designsystem.theme.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAflamiColors = staticCompositionLocalOf { AflamiLightColors }
val AflamiLightColors =  AflamiColors(
    primary =Color(0xFFF564A9),
    secondary = Color(0xFF7D1C4A),
    primaryVariant = Color(0xFFFAE6EF),
    gradientColors = GradientColors(
        overly = listOf(Color(0x00FAF5F7), Color(0xFFFAF5F7)),
        streakGradient = listOf(Color(0xFFD85895), Color(0x52D85895)),
        pointsOverly = listOf(Color(0xFFD02C7A), Color(0xFF7D1C4A))
    ),
    textColors = TextColors(
        title = Color(0xDE1F1F1F),
        body = Color(0x991F1F1F),
        hint =Color(0x611F1F1F),
        stroke =Color(0x141F1F1F),
        surface = Color(0xFFFAF5F7),
        surfaceHigh = Color(0xFFFFFFFF),
        onPrimary = Color(0xFFFFFFFF),
        onPrimaryBody =Color(0xDEFFFFFF),
        onPrimaryHint = Color(0x14FFFFFF),
        disable = Color(0xFFE1E4E5),
        iconBackground = Color(0xB30D090B),
        blurOverlay = Color(0x80FFFFFF),
        onPrimaryButton = Color(0x70FFFFFF)
    ),
    statusColors = StatusColors(
        redAccent = Color(0xFFD94C56),
        redVariant = Color(0xFFF2DFE0),
        yellowAccent = Color(0xFFFCB032),
        greenAccent = Color(0xFF429946),
        greenVariant =Color(0xFFDFF2E0),
        darkBlue =Color(0xFF0C57C8),
        blueAccent = Color(0xFF2BA3D9),
        blueCard = Color(0x3DBDD3F2),
        navyCard = Color(0x3D91A9FA),
        yellowCard = Color(0x3DFAD291),
        backgroundCircles = Color(0x3DD85895),
        profileOverly = Color(0x80FAF5F7)
    )
)