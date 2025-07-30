package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.authentication.LoginScreen
import com.example.navigation.Destination

fun NavGraphBuilder.loginRoute(
    navController: NavHostController
){
    composable(route = Destination.LoginScreen.route) {
        LoginScreen(navController = navController)
    }
}