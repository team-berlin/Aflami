package com.berlin.aflami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.berlin.aflami.navigation.AflamiNavGraph
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import org.koin.compose.getKoin
import usecase.auth.LoginUseCase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            //val getMovieDetailsUseCase: GetMovieDetailsUseCase = get()
            //val getTvShowDetailsUseCase: GetTvShowDetailsUseCase = get()

//            LaunchedEffect(Unit) {
//                //val movieDetails = getMovieDetailsUseCase(603, "en-US")
//                //Log.d("DEBUG", "Movie: ${movieDetails?.title}")
//                val tvShowDetails = getTvShowDetailsUseCase(20, "en-US")
//                Log.d("DEBUG", "Tv Show: ${tvShowDetails?.seasons[0]}")
//            }

            val usecase : LoginUseCase= getKoin<LoginUseCase>()

            AflamiTheme {
                AflamiNavGraph(
                    navController,
                    Modifier
                        .fillMaxSize()
                        .background(Theme.color.surface)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                )

            }
        }
    }
}
