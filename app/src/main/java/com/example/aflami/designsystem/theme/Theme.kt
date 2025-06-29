package com.example.aflami.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.aflami.designsystem.textstyle.AflamiTextStyle
import com.example.aflami.designsystem.textstyle.LocalAflamiTextStyle

object Theme {

    val textStyle: AflamiTextStyle
        @Composable @ReadOnlyComposable get() = LocalAflamiTextStyle.current
}