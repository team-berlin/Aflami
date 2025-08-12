package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.GameResultDestination
import com.berlin.aflami.screens.games.ResultScreen

fun NavGraphBuilder.gameResult() {
    composable<GameResultDestination> {
        ResultScreen()
    }
}