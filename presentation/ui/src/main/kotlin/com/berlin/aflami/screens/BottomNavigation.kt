package com.berlin.aflami.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.berlin.aflami.component.BottomNavItem
import com.berlin.aflami.component.NavBar
import com.berlin.designsystem.R
import com.example.navigation.Destination

@Composable
fun BottomNavigation(navController: NavController,currentRoute:String) {
    val items = listOf(
        BottomNavItem(
            icon = painterResource(id = R.drawable.home),
            route = Destination.HomeScreen.route,
            labelText = stringResource(R.string.label_home)
        ),
        BottomNavItem(
            icon = painterResource(id = R.drawable.lists),
            route = Destination.ListsScreen.route,
            labelText = stringResource(R.string.label_lists)
        ),
        BottomNavItem(
            icon = painterResource(id = R.drawable.categories),
            route = Destination.CategoriesScreen.route,
            labelText = stringResource(R.string.label_categories)
        ),
        BottomNavItem(
            icon = painterResource(id = R.drawable.letsplay),
            route = Destination.GamesScreen.route,
            labelText = stringResource(R.string.label_lets_play)
        ),
        BottomNavItem(
            icon = painterResource(id = R.drawable.profile),
            route = Destination.ProfileScreen.route,
            labelText = stringResource(R.string.label_profile)
        )
    )

    NavBar(
        navDestinations = items,
        currentRoute = currentRoute,
        onNavDestinationClicked = { route ->
            if (currentRoute != route) {
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
