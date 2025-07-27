package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.berlin.aflami.navigation.Destination
import com.berlin.aflami.screens.home.ContinueWatchingScreen
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaEffect

fun NavGraphBuilder.watchedMedia(
    navController: NavController,
) {
    composable(
        route = Destination.WatchedMediaDetails.route,
        arguments = listOf(
            navArgument(MediaDetailsArgs.ID) { NavType.LongType },
            navArgument(MediaDetailsArgs.MEDIA_TYPE) { NavType.StringType })
    ) {
        ContinueWatchingScreen(
            onEffect = { effect ->
                when (effect) {
                    is ContinueWatchingMediaEffect.NavigateToDetails -> {
                        navController.navigate(
                            Destination.MediaDetailsScreen.route(
                                effect.id, effect.type.name
                            )
                        )
                    }

                    is ContinueWatchingMediaEffect.OnBackClicked -> {
                        navController.popBackStack()
                    }
                }
            })

    }

}