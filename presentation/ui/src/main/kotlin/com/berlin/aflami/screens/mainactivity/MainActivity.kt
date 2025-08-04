package com.berlin.aflami.screens.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.berlin.aflami.navigation.AflamiNavGraph
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.main.MainActivityViewModel
import com.berlin.aflami.viewmodel.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            onBoardingViewModel.isFirstEntry.value == null
        }
        enableEdgeToEdge()
        setContent {
            val isFirstTime = onBoardingViewModel.isFirstEntry.collectAsState()

            isFirstTime.value?.let { isFirstTime ->
                AflamiTheme {
                    AflamiNavGraph(
                        navController = Theme.navController,
                        isLoggedIn = false,
                        isFirsTime = isFirstTime,
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