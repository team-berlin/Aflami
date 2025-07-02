package com.example.aflami.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.berlin.aflami.ui.theme.textstyle.LocalAflamiTextStyle
import com.berlin.aflami.ui.theme.textstyle.defaultTextStyle
import com.berlin.aflami.ui.theme.color.AflamiDarkColors
import com.example.aflami.designsystem.color.AflamiLightColors
import com.example.aflami.designsystem.color.LocalAflamiColors


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