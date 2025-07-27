package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.Destination
import com.berlin.aflami.screens.authentication.LoginScreen

fun NavGraphBuilder.loginRoute(
    navController: NavHostController
){
    composable(route = Destination.LoginScreen.route) {
        LoginScreen(navController = navController)
    }
}