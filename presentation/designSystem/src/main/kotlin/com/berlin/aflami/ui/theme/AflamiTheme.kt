package com.berlin.aflami.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.berlin.aflami.ui.color.AflamiDarkColors
import com.berlin.aflami.ui.color.AflamiLightColors
import com.berlin.aflami.ui.color.LocalAflamiColors
import com.berlin.aflami.ui.navcontroller.LocalNavController
import com.berlin.aflami.ui.textstyle.LocalAflamiTextStyle
import com.berlin.aflami.ui.textstyle.defaultTextStyle


@Composable
fun AflamiTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    selectedLanguage: String = "EN",
    content: @Composable () -> Unit
) {
    val navController = rememberNavController()
    val colors = if (isDarkTheme) AflamiDarkColors else AflamiLightColors
    val view = LocalView.current
    val layoutDirection = if (selectedLanguage == "AR") LayoutDirection.Rtl else LayoutDirection.Ltr
    DisposableEffect(isDarkTheme) {
        val activity = view.context as Activity
        WindowCompat.getInsetsController(activity.window, view).apply {
            isAppearanceLightStatusBars = !isDarkTheme
            isAppearanceLightNavigationBars = !isDarkTheme
        }

        onDispose { }
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !isDarkTheme
        }
    }


    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalAflamiColors provides colors,
        LocalAflamiTextStyle provides defaultTextStyle,
        LocalLayoutDirection provides layoutDirection
    ) {
        content()
    }
}