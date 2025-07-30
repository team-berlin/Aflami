package com.berlin.aflami.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.berlin.aflami.navigation.routes.castDetails
import com.berlin.aflami.navigation.routes.home
import com.berlin.aflami.navigation.routes.loginRoute
import com.berlin.aflami.navigation.routes.mediaDetailsRoute
import com.berlin.aflami.navigation.routes.searchByActorNameRoute
import com.berlin.aflami.navigation.routes.searchByCountryRoute
import com.berlin.aflami.navigation.routes.searchRoute
import com.berlin.aflami.navigation.routes.watchedMedia
import com.berlin.aflami.navigation.routes.webView
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.main.MainViewModel
import org.koin.compose.getKoin

/**
 * Sets up the navigation graph for the Aflami app using Jetpack Compose Navigation 2.
 *
 * This function initializes a [NavHost] with the given [navController] and defines the app's
 * navigation destinations and their respective navigation routes. The start destination is set
 * to the OnBoarding screen. Each destination is registered using its corresponding route extension
 * function to encapsulate the screen's navigation logic.
 *
 * @param navController The [NavHostController] used to manage app navigation and back stack.
 *
 */

@Composable
fun AflamiNavGraph(
//    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = getKoin().get(),
) {
    val navController = Theme.navController

    NavHost(
        navController = navController,
        startDestination = if (mainViewModel.loginState) HomeScreen else LoginScreen,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }) {

        searchRoute()
        searchByCountryRoute()
        searchByActorNameRoute()
        mediaDetailsRoute(navController)
        castDetails(navController)
        loginRoute()
        webView(navController)
        watchedMedia(navController)
        home(navController)
    }
}