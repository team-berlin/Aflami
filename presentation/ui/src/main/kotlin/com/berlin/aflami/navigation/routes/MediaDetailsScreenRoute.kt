package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.MediaDetailsDestination
import com.berlin.aflami.screens.mediadetails.screen.MediaDetailsScreen

fun NavGraphBuilder.mediaDetailsRoute() {
    composable<MediaDetailsDestination> { backStackEntry ->
        val mediaDetailsParameters = backStackEntry.toRoute<MediaDetailsDestination>()
        MediaDetailsScreen(
            mediaId = mediaDetailsParameters.mediaId,
            mediaType = mediaDetailsParameters.mediaType,
        )
    }
}