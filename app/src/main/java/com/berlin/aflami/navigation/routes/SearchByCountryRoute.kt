package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.search.worldtour.SearchByCountryScreen
import com.example.navigation.Destination.WorldTourScreen

fun NavGraphBuilder.searchByCountryRoute(
    navController: NavController
) {
    composable(route = WorldTourScreen.route) {
        SearchByCountryScreen(navController = navController)
    }
}