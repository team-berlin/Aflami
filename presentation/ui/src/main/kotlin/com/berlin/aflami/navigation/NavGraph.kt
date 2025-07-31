package com.berlin.aflami.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.berlin.aflami.navigation.routes.castDetailsScreen
import com.berlin.aflami.navigation.routes.categoriesRoute
import com.berlin.aflami.navigation.routes.gamesRoute
import com.berlin.aflami.navigation.routes.homeScreenRoute
import com.berlin.aflami.navigation.routes.listsRoute
import com.berlin.aflami.navigation.routes.loginRoute
import com.berlin.aflami.navigation.routes.mediaDetailsRoute
import com.berlin.aflami.navigation.routes.profileRoute
import com.berlin.aflami.navigation.routes.searchByActorNameRoute
import com.berlin.aflami.navigation.routes.searchByCountryRoute
import com.berlin.aflami.navigation.routes.searchScreenRoute
import com.berlin.aflami.navigation.routes.topRatingMedia
import com.berlin.aflami.navigation.routes.watchedMedia
import com.berlin.aflami.navigation.routes.webView

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
    navController: NavHostController,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            getCurrentNavBarScreen(navController)?.let { selectedRoute ->
                ShowNavigationBar(selectedRoute, navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) NavigationBarDestinations.HomeScreen else LoginDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            loginNavigationGraph()
            bottomNavigationBarGraph()
            homeNavigationGraph()
            detailsNavigationGraph()
        }
    }
}

@Composable
private fun ShowNavigationBar(
    selectedRoute: NavigationBarDestinations,
    navController: NavHostController,
) {
    NavBar(
        navDestinations = bottomNavList,
        currentRoute = selectedRoute,
        onNavDestinationClicked = { route ->
            if (route != selectedRoute) {
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
    )
}

@Composable
private fun getCurrentNavBarScreen(navController: NavHostController): NavigationBarDestinations? {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = backStackEntry?.destination?.route
    val currentNavigationBarDestinationsDestination: NavigationBarDestinations? =
        bottomNavBarDestinationsMap[currentRoute]
    return currentNavigationBarDestinationsDestination
}

fun NavGraphBuilder.bottomNavigationBarGraph() {
    homeScreenRoute()
    listsRoute()
    profileRoute()
    categoriesRoute()
    gamesRoute()
}

fun NavGraphBuilder.searchNavigationGraph() {
    searchScreenRoute()
    searchByCountryRoute()
    searchByActorNameRoute()
}

fun NavGraphBuilder.loginNavigationGraph() {
    loginRoute()
    webView()
}

fun NavGraphBuilder.homeNavigationGraph() {
    searchNavigationGraph()
    watchedMedia()
    topRatingMedia()
}

fun NavGraphBuilder.detailsNavigationGraph() {
    mediaDetailsRoute()
    castDetailsScreen()
}