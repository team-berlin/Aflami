package com.berlin.aflami.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.berlin.aflami.ui.textstyle.AflamiTextStyle
import com.berlin.aflami.ui.textstyle.LocalAflamiTextStyle
import com.berlin.aflami.ui.color.AflamiColors
import com.berlin.aflami.ui.color.LocalAflamiColors

object Theme {
    val color: AflamiColors
        @Composable @ReadOnlyComposable get() = LocalAflamiColors.current
    val textStyle: AflamiTextStyle
        @Composable @ReadOnlyComposable get() = LocalAflamiTextStyle.current
}