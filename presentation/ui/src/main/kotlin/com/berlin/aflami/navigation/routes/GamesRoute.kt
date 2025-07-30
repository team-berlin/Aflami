package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.games.GamesScreen
import com.example.navigation.Destination

fun NavGraphBuilder.gamesRoute(
    navController: NavController
) {
    composable(route = Destination.GamesScreen.route) {
        GamesScreen(navController = navController)
    }
}