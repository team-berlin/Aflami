package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.mediadetails.screen.MediaDetailsScreen
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.example.navigation.Destination.MediaDetailsScreen

fun NavGraphBuilder.mediaDetailsRoute(
    navController: NavController
) {
    composable(route = MediaDetailsScreen.route) {
        MediaDetailsScreen(
            navController = navController,
            mediaId = 22,
            mediaType = MediaType.MOVIE
        )
    }
}