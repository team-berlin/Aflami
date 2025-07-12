package com.berlin.aflami.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.berlin.aflami.ui.textstyle.LocalAflamiTextStyle
import com.berlin.aflami.ui.textstyle.defaultTextStyle
import com.berlin.aflami.ui.color.AflamiDarkColors
import com.berlin.aflami.ui.color.AflamiLightColors
import com.berlin.aflami.ui.color.LocalAflamiColors


@Composable
fun AflamiTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) AflamiDarkColors else AflamiLightColors
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    CompositionLocalProvider(
        LocalAflamiColors provides colors,
        LocalAflamiTextStyle provides defaultTextStyle
    ) {
        content()
    }
}