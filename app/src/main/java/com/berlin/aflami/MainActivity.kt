package com.berlin.aflami

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.berlin.aflami.navigation.AflamiNavGraph
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.main.MainViewModel
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel = getKoin().get()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            mainViewModel.isLoading
        }

        setContent {
            val navController = rememberNavController()
            AflamiTheme {
                AflamiNavGraph(
                    navController = navController,
                    isLoggedIn = mainViewModel.loginState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.color.surface)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                )
            }
        }
    }
}
