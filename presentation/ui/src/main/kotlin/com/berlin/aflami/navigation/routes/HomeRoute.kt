package com.berlin.aflami.navigation.routes


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.Destination
import com.berlin.aflami.screens.home.HomeScreen
import com.berlin.aflami.viewmodel.home.HomeScreenEffect

fun NavGraphBuilder.home(
    navController: NavController,
) {
    composable(
        route = Destination.HomeScreen.route,
    ) {
        HomeScreen(navController)

    }

}