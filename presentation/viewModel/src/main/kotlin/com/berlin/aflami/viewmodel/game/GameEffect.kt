package com.berlin.aflami.viewmodel.game

import com.berlin.exception.AflamiException

sealed class GameEffect {
    data class NavigateToGuessGameScreen(
        val gameType: String,
        val numberOfQuestion: Int,
        val numberOfPoint: Int,
        val time: Int
    ) : GameEffect()

    data class ShowError(val error: AflamiException) : GameEffect()
}