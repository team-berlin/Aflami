package com.berlin.aflami.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.berlin.aflami.navigation.routes.castDetailsScreen
import com.berlin.aflami.navigation.routes.homeScreenRoute
import com.berlin.aflami.navigation.routes.loginRoute
import com.berlin.aflami.navigation.routes.mediaDetailsRoute
import com.berlin.aflami.navigation.routes.searchByActorNameRoute
import com.berlin.aflami.navigation.routes.searchByCountryRoute
import com.berlin.aflami.navigation.routes.searchScreenRoute
import com.berlin.aflami.navigation.routes.topRatingMedia
import com.berlin.aflami.navigation.routes.watchedMedia
import com.berlin.aflami.navigation.routes.webView
import com.berlin.aflami.ui.theme.Theme

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
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean,
) {
    val startDestination =
        if (isLoggedIn) HomeScreen else LoginScreen
    val navController = Theme.navController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }) {

        searchScreenRoute()
        searchByCountryRoute()
        searchByActorNameRoute()
        mediaDetailsRoute()
        castDetailsScreen()
        loginRoute()
        webView()
        watchedMedia()
        topRatingMedia()
        homeScreenRoute()
    }
}
