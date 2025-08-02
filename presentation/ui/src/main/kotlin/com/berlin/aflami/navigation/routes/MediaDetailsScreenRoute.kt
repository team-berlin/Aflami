package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.MediaDetailsDestination
import com.berlin.aflami.screens.mediadetails.screen.MediaDetailsScreen

fun NavGraphBuilder.mediaDetailsRoute() {
    composable<MediaDetailsDestination> { backStackEntry ->
        MediaDetailsScreen(

        )
    }
}