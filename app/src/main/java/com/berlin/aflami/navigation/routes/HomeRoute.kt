package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.home.screen.HomeScreen
import com.example.navigation.Destination.SearchScreen

fun NavGraphBuilder.homeRoute(
    navController: NavController
) {
    composable(route = SearchScreen.route) {
        HomeScreen(navController = navController)
    }
}