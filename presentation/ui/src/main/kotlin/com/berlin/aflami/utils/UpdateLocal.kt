package com.berlin.aflami.utils

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import java.util.Locale


@SuppressLint("LocalContextConfigurationRead")
@Composable
fun UpdateLocale(selectedLanguage: String) {
    val context = LocalContext.current
    DisposableEffect(selectedLanguage) {
        val locale = when (selectedLanguage) {
            "AR" -> Locale("ar")
            "EN" -> Locale("en")
            else -> Locale("en")
        }
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        onDispose {}
    }
}