package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.CastScreen
import com.berlin.aflami.screens.mediadetails.screen.CastDetailsScreen

fun NavGraphBuilder.castDetailsScreen() = composable<CastScreen> { backStackEntry ->
    val castScreenParameters = backStackEntry.toRoute<CastScreen>()
    CastDetailsScreen(castScreenParameters.mediaId,castScreenParameters.mediaType)
}