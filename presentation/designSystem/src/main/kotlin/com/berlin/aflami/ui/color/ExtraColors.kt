package com.berlin.aflami.ui.color

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object ExtraColors {
    val darkReddishPink12 = Color(0x1FBF434C)
    val darkReddishGreen12 = Color(0x1F429946)
    val white = Color(0xFFFFFFFF)

    val blueLinearGradient = Brush.linearGradient(
        listOf(
            Color(0xFF53ABF9),
            Color(0xFF336490),
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY),
    )

    val darkPurpleLinearGradient = Brush.linearGradient(
        listOf(
            Color(0xFFD85895),
            Color(0xFF803559)
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY),
    )

    val overlayGradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color(0xFF0D090B)
        )
    )

    val black50 = Color(0x80000000)

    val primaryGredient=Brush.linearGradient(
        listOf(
            Color(0xFFF564A9),
            Color(0xFF973A66)
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY)

    )
    val BackgroundGradient=Brush.linearGradient(
        listOf(
            Color(0xCC63163A),
            Color(0x0063163A),
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY)

    )

    val onBoardingLinearGradientTopToDown=Brush.verticalGradient(
        listOf(
            Color(0x000D0608),
            Color(0xFF0D0608)
        ),

    )
    val onBoardingLinearGradientDownToTop=Brush.verticalGradient(
        listOf(
            Color(0xFF0D0608),
            Color(0x000D0608),
        ),

    )


}