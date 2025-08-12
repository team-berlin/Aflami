package com.berlin.aflami.viewmodel.game

interface GameInteractionListener {
    fun onGameInfoClicked(gameType: GameType)
    fun onGameLevelClicked(gameLevel: GameLevel)
    fun onShowLevelDialog()
    fun onDismissLevelDialog()
}