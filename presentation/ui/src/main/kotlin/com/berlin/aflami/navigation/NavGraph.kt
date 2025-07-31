package com.berlin.aflami.navigation

import BottomNavBar
import BottomNavigationBar
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.berlin.aflami.component.BottomNavItem
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
    val startDestination = if (isLoggedIn) NavBar.HomeScreen else LoginScreen
    val navController = Theme.navController

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val bottomNavItems = BottomNavItems.entries.map {
        BottomNavItem(
            icon = painterResource(id = it.icon),
            labelText = stringResource(id = it.label),
            route = it.route.toString()
        )
    }
    val bottomRoutes = BottomNavItems.entries.map { it.route.toString() }
    val shouldShowBottomBar = currentRoute in bottomRoutes


    Scaffold(
        modifier = modifier,
        if (shouldShowBottomBar) {
            NavBar(
                navDestinations = bottomNavItems,
                currentRoute = currentRoute ?: "",
                onNavDestinationClicked = { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
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
            listsRoute()
            profileRoute()
            categoriesRoute()
            gamesRoute()
        }
    }
}