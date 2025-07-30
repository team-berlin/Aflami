package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.MediaDetails
import com.berlin.aflami.screens.mediadetails.screen.MediaDetailsScreen

fun NavGraphBuilder.mediaDetailsRoute() {
    composable<MediaDetails> { backStackEntry ->
        val mediaDetailsParameters = backStackEntry.toRoute<MediaDetails>()
        MediaDetailsScreen(
            mediaId = mediaDetailsParameters.mediaId,
            mediaType = mediaDetailsParameters.mediaType,
        )
    }
}