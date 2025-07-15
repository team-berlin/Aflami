package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.mediadetails.screen.CastScreen
import com.example.navigation.Destination

fun NavGraphBuilder.castDetails(
    navController: NavController
){
    composable(route = Destination.CastScreen.route) {
        CastScreen(navController = navController)
    }

}