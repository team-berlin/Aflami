package com.berlin.aflami.screens.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.berlin.aflami.navigation.AflamiNavGraph
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.utils.UpdateLocale
import com.berlin.aflami.viewmodel.main.MainActivityViewModel
import com.berlin.aflami.viewmodel.profile.ProfileScreenEffect
import com.berlin.aflami.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            mainActivityViewModel.state.value.isLoading
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainState by mainActivityViewModel.state.collectAsState()
            val isDarkTheme = mainState.isDarkThemeEnabled
            LaunchedEffect(Unit) {
                profileViewModel.effect.collect { effect ->
                    when (effect) {
                        is ProfileScreenEffect.RefreshActivity -> recreate()
                        else -> Unit
                    }
                }
            }
            UpdateLocale(mainState.selectedLanguage)
            AflamiTheme(
                isDarkTheme = isDarkTheme,
                selectedLanguage = mainState.selectedLanguage
            ) {
                if (!mainState.isLoading) {
                    AflamiNavGraph(
                        navController = Theme.navController,
                        isLoggedIn = mainState.isLoggedIn,
                        isFirsTime = mainState.isFirstEntry,
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