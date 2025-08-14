package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.GameResultDestination
import com.berlin.aflami.screens.games.ResultScreen

fun NavGraphBuilder.gameResult() {
    composable<GameResultDestination> {backStackEntry ->
        val args = backStackEntry.toRoute<GameResultDestination>()

        ResultScreen(
            totalTime = args.totalTime,
            gameType =args.gameType,
            numberOfQuestion = args.numberOfQuestion,
            numberOfPoints = args.numberOfPoints,
            totalPoint = args.totalPoint,
            time = args.time,
        )
    }
}