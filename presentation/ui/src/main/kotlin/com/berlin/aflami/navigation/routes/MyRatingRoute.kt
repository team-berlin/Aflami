package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.MyRatingDestination
import com.berlin.aflami.screens.profile.MyRatingScreen

fun NavGraphBuilder.myRating() = composable<MyRatingDestination> {
    MyRatingScreen()
}