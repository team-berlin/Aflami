package com.berlin.aflami.ui.theme.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.berlin.aflami.ui.theme.textstyle.AflamiTextStyle
import com.berlin.aflami.ui.theme.textstyle.LocalAflamiTextStyle
import com.berlin.aflami.ui.theme.color.AflamiColors
import com.berlin.aflami.ui.theme.color.LocalAflamiColors

object Theme {
    val color: AflamiColors
        @Composable @ReadOnlyComposable get() = LocalAflamiColors.current
    val textStyle: AflamiTextStyle
        @Composable @ReadOnlyComposable get() = LocalAflamiTextStyle.current
}