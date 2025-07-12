package com.berlin.aflami.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.search.screen.SearchScreen
import com.example.navigation.Destination.SearchScreen

fun NavGraphBuilder.searchRoute(
    navController: NavController
) {
    composable(route = SearchScreen.route) {
        SearchScreen(navController = navController)
    }
}