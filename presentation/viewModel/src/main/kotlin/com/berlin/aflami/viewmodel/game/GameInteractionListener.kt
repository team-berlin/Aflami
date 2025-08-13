package com.berlin.aflami.viewmodel.game

interface GameInteractionListener {
    fun onGameInfoClicked(
         gameType: String,
         numberOfQuestion: Int,
         numberOfPoint: Int,
         time: Int
    )
    fun onShowLevelDialog()
    fun onDismissLevelDialog()
}