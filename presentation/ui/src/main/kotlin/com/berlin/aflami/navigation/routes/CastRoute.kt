package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.CastScreen
import com.berlin.aflami.screens.mediadetails.screen.CastDetailsScreen
import com.berlin.aflami.viewmodel.mediadetails.cast.CastDetailsEffect

fun NavGraphBuilder.castDetails(
    navController: NavController
){
    composable<CastScreen> { backStackEntry ->
        val castScreenParameters = backStackEntry.toRoute<CastScreen>()
        CastDetailsScreen(
            id = castScreenParameters.mediaId,
            mediaType = castScreenParameters.mediaType,
                    onEffect = {effect->
                when (effect) {
                    is CastDetailsEffect.CastNavigationBack -> navController.popBackStack()
                }
            },
        )
    }

}