package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.profile.ProfileScreen
import com.example.navigation.Destination

fun NavGraphBuilder.profileRoute(
    navController: NavController
) {
    composable(route = Destination.ProfileScreen.route) {
        ProfileScreen(navController = navController)
    }
}