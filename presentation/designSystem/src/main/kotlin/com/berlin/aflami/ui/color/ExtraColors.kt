package com.berlin.aflami.ui.color

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.berlin.aflami.ui.theme.Theme

object ExtraColors {
    val darkReddishPink12 = Color(0x1FBF434C)
    val darkReddishGreen12 = Color(0x1F429946)

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
}