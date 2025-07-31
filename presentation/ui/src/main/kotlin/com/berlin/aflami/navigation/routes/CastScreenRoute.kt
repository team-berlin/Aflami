package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.CastDestination
import com.berlin.aflami.screens.mediadetails.screen.CastDetailsScreen

fun NavGraphBuilder.castDetailsScreen() = composable<CastDestination> { backStackEntry ->
    val castDestinationParameters = backStackEntry.toRoute<CastDestination>()
    CastDetailsScreen(id = castDestinationParameters.mediaId, mediaType = castDestinationParameters.mediaType)
}