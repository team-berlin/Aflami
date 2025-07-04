package com.example.aflami.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.aflami.designsystem.textstyle.AflamiTextStyle
import com.example.aflami.designsystem.textstyle.LocalAflamiTextStyle
import com.example.aflami.designsystem.color.AflamiColors
import com.example.aflami.designsystem.color.LocalAflamiColors

object Theme {
    val color: AflamiColors
        @Composable @ReadOnlyComposable get() = LocalAflamiColors.current
    val textStyle: AflamiTextStyle
        @Composable @ReadOnlyComposable get() = LocalAflamiTextStyle.current
}