package com.berlin.aflami.onboarding

import androidx.compose.ui.graphics.painter.Painter

data class OnBoardingModel(
    val image: Painter,
    val title: String,
    val description: String
)
