package com.berlin.aflami.screens.mainactivity

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.berlin.aflami.navigation.AflamiNavGraph
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.main.MainActivityViewModel
import com.berlin.aflami.viewmodel.profile.ProfileScreenEffect
import com.berlin.aflami.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        splashScreen.setOnExitAnimationListener { splashView ->
            (splashView.iconView as? Animatable)?.start()
            splashView.iconView.animate()
                .alpha(0.5f)
                .setDuration(3000)
                .withEndAction { splashView.remove() }
                .start()
        }
        splashScreen.setKeepOnScreenCondition {
            mainActivityViewModel.state.value.isLoading
        }

        enableEdgeToEdge()
        setContent {
            val profileState by profileViewModel.state.collectAsState()
            val isDarkTheme = profileState.isDarkThemeEnabled
            LaunchedEffect(Unit) {
                profileViewModel.effect.collect { effect ->
                    when (effect) {
                        is ProfileScreenEffect.RefreshActivity -> recreate()
                        else -> Unit
                    }
                }
            }
            UpdateLocale(profileState.selectedLanguage)
            AflamiTheme(isDarkTheme = isDarkTheme) {
                val mainState by mainActivityViewModel.state.collectAsState()

                if (!mainState.isLoading) {
                    AflamiNavGraph(
                        navController = Theme.navController,
                        isLoggedIn = mainState.isLoggedIn,
                        isFirsTime = mainState.isFirstEntry,
                        selectedLanguage = profileState.selectedLanguage,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.color.surface)
                            .navigationBarsPadding()
                    )
                }
            }
        }
    }
}


@SuppressLint("LocalContextConfigurationRead")
@Composable
fun UpdateLocale(selectedLanguage: String) {
    val context = LocalContext.current
    DisposableEffect(selectedLanguage) {
        val locale = when (selectedLanguage) {
            "AR" -> Locale("ar")
            "EN" -> Locale("en")
            else -> Locale.getDefault()
        }
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        onDispose {}
    }
}