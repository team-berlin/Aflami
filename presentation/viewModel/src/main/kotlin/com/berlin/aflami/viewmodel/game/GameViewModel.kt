package com.berlin.aflami.viewmodel.game

import com.berlin.aflami.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : BaseViewModel<GameScreenState, GameEffect>(GameScreenState()), GameInteractionListener {

    init {
        loadData()
    }

    private fun loadData() {
        val levels = listOf(
            GameLevel(10, 100, GameLevelType.EASY, 30),
            GameLevel(10, 100, GameLevelType.MEDIUM, 30),
            GameLevel(10, 100, GameLevelType.HARD, 30)
        )

        updateState {
            it.copy(
                points = 100, // TODO: Replace with getPointsUseCase()
                gameLevel = levels
            )
        }
    }

    override fun onGameInfoClicked(gameType: GameType) =
        updateState { it.copy(selectedGameType = gameType) }

    override fun onGameLevelClicked(gameLevel: GameLevel) =
        updateState { it.copy(selectedLevel = gameLevel) }

    override fun onShowLevelDialog() =
        updateState { it.copy(showDialog = true) }

    override fun onDismissLevelDialog() =
        updateState { it.copy(showDialog = false) }
}
