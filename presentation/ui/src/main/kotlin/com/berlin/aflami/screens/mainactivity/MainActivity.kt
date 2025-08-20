package com.berlin.aflami.screens.mainactivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.berlin.aflami.navigation.AflamiNavGraph
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private var startTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        startTime = System.currentTimeMillis()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            mainActivityViewModel.state.value.isLoading || System.currentTimeMillis() < startTime + 3000
        }


        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val mainState by mainActivityViewModel.state.collectAsState()

            AflamiTheme(
                isDarkTheme = mainState.isDark
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

