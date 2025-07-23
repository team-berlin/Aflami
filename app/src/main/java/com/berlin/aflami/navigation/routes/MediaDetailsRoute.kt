package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.berlin.aflami.screens.mediadetails.screen.MediaDetailsScreen
import com.example.navigation.Destination.MediaDetailsScreen


fun NavGraphBuilder.mediaDetailsRoute(
    navController: NavController
) {
    composable(
        route = MediaDetailsScreen.route,
        arguments = listOf(
            navArgument(MediaDetailsArgs.ID) { NavType.LongType },
            navArgument(MediaDetailsArgs.MEDIA_TYPE) { NavType.StringType }
        )
    ) {
        MediaDetailsScreen(
            navController = navController,
        )

    }

}

object MediaDetailsArgs {
    const val ID = "id"
    const val MEDIA_TYPE = "media_type"
}
