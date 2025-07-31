package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.NavigationBarDestinations
import com.berlin.aflami.screens.profile.ProfileScreen

fun NavGraphBuilder.profileRoute()=composable<NavigationBarDestinations.ProfileScreen> {
        ProfileScreen()
    }