package com.berlin.aflami

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.berlin.aflami.routes.searchByActorNameRoute
import com.berlin.aflami.routes.searchRoute
import com.berlin.aflami.routes.worldTourRoute
import com.example.navigation.Destination

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
fun AflamiNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = Destination.SearchScreen.route) {
        searchRoute(navController)
        worldTourRoute(navController)
        searchByActorNameRoute(navController)
    }
}