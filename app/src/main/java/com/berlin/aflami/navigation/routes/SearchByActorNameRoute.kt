package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Destination
import com.berlin.aflami.screens.search.actor.SearchByActorNameScreen

fun NavGraphBuilder.searchByActorNameRoute(
    navController: NavController
) {
    composable(route = Destination.SearchByActorNameScreen.route) {
        SearchByActorNameScreen(navController = navController)
    }
}