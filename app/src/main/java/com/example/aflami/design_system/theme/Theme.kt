package com.example.aflami.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.aflami.design_system.textstyle.AflamiTextStyle
import com.example.aflami.design_system.textstyle.LocalAflamiTextStyle

object Theme {

    val textStyle: AflamiTextStyle
        @Composable @ReadOnlyComposable get() = LocalAflamiTextStyle.current
}