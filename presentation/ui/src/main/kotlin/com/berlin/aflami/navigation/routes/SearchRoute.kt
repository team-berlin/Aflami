package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.search.search.SearchScreen
import com.berlin.aflami.navigation.Destination
fun NavGraphBuilder.searchRoute(
    navController: NavController
) {
    composable(route = Destination.SearchScreen.route) {
        SearchScreen(navController = navController)
    }
}