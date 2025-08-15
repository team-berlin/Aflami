package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.screens.mediadetails.screen.TvShowDetailsScreen

fun NavGraphBuilder.tvShowDetailsRoute() {
    composable<TVShowDetailsDestination> { backStackEntry ->
        TvShowDetailsScreen()
    }
}