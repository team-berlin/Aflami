package com.berlin.aflami.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.berlin.aflami.component.BottomNavItem
import com.berlin.aflami.component.NavBar
import com.berlin.designsystem.R
import com.example.navigation.Destination

@Composable
fun BottomNavigation(navController: NavController, currentRoute: String) {
    val items = remember {
        navConfig
    }.map { (iconRes, labelRes, route) ->
        BottomNavItem(
            icon = painterResource(id = iconRes),
            route = route,
            labelText = stringResource(id = labelRes)
        )
    }
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

private val navConfig = listOf(
    Triple(R.drawable.home, R.string.label_home, Destination.HomeScreen.route),
    Triple(R.drawable.lists, R.string.label_lists, Destination.ListsScreen.route),
    Triple(R.drawable.categories, R.string.label_categories, Destination.CategoriesScreen.route),
    Triple(R.drawable.letsplay, R.string.label_lets_play, Destination.GamesScreen.route),
    Triple(R.drawable.profile, R.string.label_profile, Destination.ProfileScreen.route)
)
