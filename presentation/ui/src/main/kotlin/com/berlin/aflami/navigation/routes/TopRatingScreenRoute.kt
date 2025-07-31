package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.TopRatingMediaDestination
import com.berlin.aflami.screens.home.TopRatingScreen

fun NavGraphBuilder.topRatingMedia() = composable<TopRatingMediaDestination>{
        TopRatingScreen()
}

