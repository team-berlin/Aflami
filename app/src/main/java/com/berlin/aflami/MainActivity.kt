package com.berlin.aflami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.berlin.aflami.screens.search.country.SearchByCountryScreen
import com.berlin.aflami.ui.theme.AflamiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            AflamiTheme {
//                AflamiNavGraph(
//                    navController,
//                    Modifier
//                        .fillMaxSize()
//                        .background(Theme.color.surface)
//                        .statusBarsPadding()
//                        .navigationBarsPadding()
//                )
                SearchByCountryScreen(
                    navController = navController
                )
            }
        }
    }
}
