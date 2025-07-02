package com.berlin.aflami.ui.color

import androidx.compose.ui.graphics.Color


val AflamiDarkColors = AflamiColors(
    primary = Color(0xFFD85895),
    secondary = Color(0xFF64163B),
    primaryVariant = Color(0xFF1F1218),
    stroke = Color(0x14FFFFFF),
    surface = Color(0xFF0D090B),
    disable = Color(0xFF292828),
    iconBackground = Color(0xB20D090B),
    blurOverlay = Color(0x7F000000),
    onPrimaryButton = Color(0x14FFFFFF),
    surfaceHigh = Color(0xFF141112),
    gradientColors = GradientColors(
        overly = listOf(Color(0x000D090B), Color(0xFF0D090B)),
        streakGradient = listOf(Color(0x80FFFFFF), Color(0x1FFFFFFF)),
        pointsOverly = listOf(Color(0xFF3B0D23), Color(0xFF7D1C4A))
    ),
    textColors = TextColors(
        title = Color(0xDEFFFFFF),
        body = Color(0x99FFFFFF),
        hint = Color(0x61FFFFFF),
        onPrimary = Color(0xDEFFFFFF),
        onPrimaryBody = Color(0x7FFFFFFF),
        onPrimaryHint = Color(0x14FFFFFF),

        ),
    statusColors = StatusColors(
        redAccent = Color(0xFFA63A42),
        redVariant = Color(0xFF1F1213),
        yellowAccent = Color(0xFFE5A02E),
        greenAccent = Color(0xFF3D8C40),
        greenVariant = Color(0xFF0C140D),
        darkBlue = Color(0xFF0C57C8),
        blueAccent = Color(0xFF2BA3D9),
        blueCard = Color(0xFF121B1F),
        navyCard = Color(0xFF12151F),
        yellowCard = Color(0xFF1F1A12),
        backgroundCircles = Color(0x14FFFFFF),
        profileOverly = Color(0x800D090B)
    )
)