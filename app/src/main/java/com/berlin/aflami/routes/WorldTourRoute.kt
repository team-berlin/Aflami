package com.berlin.aflami.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.search.worldtour.WorldTourScreen
import com.example.navigation.Destination.WorldTourScreen

fun NavGraphBuilder.worldTourRoute(
    navController: NavController
) {
    composable(route = WorldTourScreen.route) {
        WorldTourScreen(navController = navController)
    }
}