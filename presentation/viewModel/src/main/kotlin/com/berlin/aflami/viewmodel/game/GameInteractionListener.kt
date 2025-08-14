package com.berlin.aflami.viewmodel.game

interface GameInteractionListener {
    fun onSelectGameType(gameType: GameType)
    fun onSelectLevel(levelIndex: Int)

    fun onGameInfoClicked(
        gameType: GameType,
        numberOfQuestion: Int,
        numberOfPoint: Int,
        time: Int
    )

    fun onShowLevelDialog()
    fun onDismissLevelDialog()
}
