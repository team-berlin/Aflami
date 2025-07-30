package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.LoginScreen
import com.berlin.aflami.screens.authentication.LoginScreen

fun NavGraphBuilder.loginRoute() = composable<LoginScreen> {
    LoginScreen()
}