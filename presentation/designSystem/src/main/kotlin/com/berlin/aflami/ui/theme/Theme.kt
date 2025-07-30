package com.berlin.aflami.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.navigation.NavHostController
import com.berlin.aflami.ui.color.AflamiColors
import com.berlin.aflami.ui.color.LocalAflamiColors
import com.berlin.aflami.ui.navcontroller.LocalNavController
import com.berlin.aflami.ui.textstyle.AflamiTextStyle
import com.berlin.aflami.ui.textstyle.LocalAflamiTextStyle

object Theme {
    val color: AflamiColors
        @Composable @ReadOnlyComposable get() = LocalAflamiColors.current
    val textStyle: AflamiTextStyle
        @Composable @ReadOnlyComposable get() = LocalAflamiTextStyle.current
    val navController: NavHostController
        @Composable @ReadOnlyComposable get() = LocalNavController.current

}