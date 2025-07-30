package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.berlin.aflami.screens.home.ContinueWatchingScreen
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaEffect
import com.example.navigation.Destination

fun NavGraphBuilder.watchedMedia(
    navController: NavController,
) {
    composable(
        route = Destination.WatchedMediaDetails.route,
        arguments = listOf(
            navArgument(MediaDetailsArgs.ID) { NavType.LongType },
            navArgument(MediaDetailsArgs.MEDIA_TYPE) { NavType.StringType })
    ) {
        ContinueWatchingScreen(navController = navController)

    }

}