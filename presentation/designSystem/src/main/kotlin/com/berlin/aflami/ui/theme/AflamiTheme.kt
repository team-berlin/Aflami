package com.berlin.aflami.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

    CompositionLocalProvider(
        LocalAflamiColors provides colors,
        LocalAflamiTextStyle provides defaultTextStyle
    ) {
        content()
    }
}