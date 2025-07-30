package com.berlin.aflami.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.currentBackStackEntryAsState
import com.berlin.aflami.navigation.routes.castDetails
import com.berlin.aflami.navigation.routes.categoriesRoute
import com.berlin.aflami.navigation.routes.gamesRoute
import com.berlin.aflami.navigation.routes.categoriesRoute
import com.berlin.aflami.navigation.routes.gamesRoute
import com.berlin.aflami.navigation.routes.home
import com.berlin.aflami.navigation.routes.listsRoute
import com.berlin.aflami.navigation.routes.listsRoute
import com.berlin.aflami.navigation.routes.loginRoute
import com.berlin.aflami.navigation.routes.mediaDetailsRoute
import com.berlin.aflami.navigation.routes.profileRoute
import com.berlin.aflami.navigation.routes.profileRoute
import com.berlin.aflami.navigation.routes.searchByActorNameRoute
import com.berlin.aflami.navigation.routes.searchByCountryRoute
import com.berlin.aflami.navigation.routes.searchRoute
import com.berlin.aflami.navigation.routes.topRatingMedia
import com.berlin.aflami.navigation.routes.topRatingMedia
import com.berlin.aflami.navigation.routes.watchedMedia
import com.berlin.aflami.navigation.routes.webView
import com.berlin.aflami.screens.BottomNavigation
import com.berlin.aflami.screens.BottomNavigation
import com.berlin.aflami.viewmodel.main.MainViewModel
import com.example.navigation.Destination
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
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
) {

    val startDestination = if (isLoggedIn) Destination.HomeScreen.route else Destination.LoginScreen.route
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val bottomNavScreens = listOf(
        Destination.HomeScreen.route,
        Destination.ProfileScreen.route,
        Destination.ListsScreen.route,
        Destination.CategoriesScreen.route,
        Destination.GamesScreen.route,
    )
    val shouldShowBottomBar = currentRoute in bottomNavScreens

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavigation(navController,currentRoute?:"")
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            searchRoute(navController)
            searchByCountryRoute(navController)
            searchByActorNameRoute(navController)
            mediaDetailsRoute(navController)
            castDetails(navController)
            loginRoute(navController)
            webView(navController)
            watchedMedia(navController)
            home(navController)
            profileRoute(navController)
            listsRoute(navController)
            categoriesRoute(navController)
            gamesRoute(navController)
            topRatingMedia(navController)
        }
    }
}
