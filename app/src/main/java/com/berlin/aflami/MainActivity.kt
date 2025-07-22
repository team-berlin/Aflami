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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.berlin.aflami.navigation.AflamiNavGraph
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.entity.auth.RequestToken
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import repository.AuthenticationRepository
import usecase.auth.LoginUseCase
import org.koin.android.ext.android.get
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
        val repository: AuthenticationRepository = get()
        lifecycleScope.launch {
            Log.d("AUTH", "inside coroutiescope before requesting token")
           val requesttoken= repository.requestToken()
            Log.d("AUTH", "inside coroutiescope after requesting token $requesttoken")
           val token = requesttoken.requestToken
            Log.d("AUTH", "inside coroutiescope after requesting token $token")
           val loginToken= repository.login("testdevdroid","D#HS6i25Rb-!f4X",token)
            Log.d("AUTH", "inside coroutiescope logingin $loginToken")
            val session = repository.createSession(
               loginToken.requestToken
            )
            Log.d("AUTH", "inside coroutiescope after requesting token $session")

        }
    }
}
