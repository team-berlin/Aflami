package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.mediadetails.screens.MediaDetailsScreen
import com.example.navigation.Destination.MediaDetailsScreen


fun NavGraphBuilder.mediaDetailsRoute(
    navController: NavController
) {
    composable(route = MediaDetailsScreen.route) {
        MediaDetailsScreen(navController = navController)
    }
}