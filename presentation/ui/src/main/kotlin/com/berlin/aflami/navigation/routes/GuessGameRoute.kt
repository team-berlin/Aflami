package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.GuessGameDestination
import com.berlin.aflami.screens.games.GuessTheGameScreen

fun NavGraphBuilder.guessGame() {
    composable<GuessGameDestination> {
        GuessTheGameScreen()
    }
}