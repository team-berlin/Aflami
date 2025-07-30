package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.berlin.aflami.navigation.Destination
import com.berlin.aflami.screens.mediadetails.screen.CastDetailsScreen
import com.berlin.aflami.viewmodel.mediadetails.cast.CastDetailsEffect

fun NavGraphBuilder.castDetails(
    navController: NavController
){
    composable(
        route = Destination.CastScreen.route,
        arguments = listOf(
            navArgument(MediaDetailsArgs.ID) { NavType.LongType },
        navArgument(MediaDetailsArgs.MEDIA_TYPE) { NavType.StringType }
    )
    ) {
        CastDetailsScreen(
            onEffect = {effect->
                when (effect) {
                    is CastDetailsEffect.CastNavigationBack -> navController.popBackStack()
                }
            }
        )
    }

}