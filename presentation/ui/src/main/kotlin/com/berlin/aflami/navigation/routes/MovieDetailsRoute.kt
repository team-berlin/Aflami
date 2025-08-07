package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.screens.mediadetails.screen.MovieDetailsScreen

fun NavGraphBuilder.movieDetailsRoute() {
    composable<MovieDetailsDestination> { backStackEntry ->
        MovieDetailsScreen()
    }
}