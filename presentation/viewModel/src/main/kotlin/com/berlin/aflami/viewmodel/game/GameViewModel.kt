package com.berlin.aflami.viewmodel.game

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatusUseCase
import usecase.game.GetPointsUseCase
import usecase.profile.GetUserProfileUseCase
import usecase.profile.ObserveUserProfileUseCase

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
) : BaseViewModel<GameScreenState, GameEffect>(GameScreenState()), GameInteractionListener {

    private var userId=0
    init {
        loadData()
        collectUserProfile()
    }

    private fun loadData() {
        val levels = listOf(
            GameLevel(5, 5, GameLevelType.EASY, 45),
            GameLevel(10, 10, GameLevelType.MEDIUM, 30),
            GameLevel(20, 20, GameLevelType.HARD, 10)
        )
        tryToCall(
            call = {
                getPointsUseCase(userId)
            },
            onSuccess = { points ->
                updateState { it.copy(points = points, gameLevel = levels) }
            },
            onError = { updateState { it.copy(points = 0, gameLevel = levels) } }
        )
    }

    override fun onSelectGameType(gameType: GameType) {
        updateState { it.copy(selectedGameType = gameType) }
    }

    override fun onSelectLevel(levelIndex: Int) {
        val level = state.value.gameLevel.getOrNull(levelIndex)
        updateState { it.copy(selectedLevel = level) }
    }

    override fun onGameInfoClicked(
        gameType: String,
        numberOfQuestion: Int,
        numberOfPoint: Int,
        time: Int
    ) {
        sendNewEffect(
            GameEffect.NavigateToGuessGameScreen(
                gameType = gameType,
                numberOfQuestion = numberOfQuestion,
                numberOfPoint = numberOfPoint,
                time = time
            )
        )
    }

    override fun onShowLevelDialog() =
        updateState { it.copy(showDialog = true) }

    override fun onDismissLevelDialog() =
        updateState { it.copy(showDialog = false) }

    private fun collectUserProfile() {
        viewModelScope.launch {
            observeUserProfileUseCase()
                .collect { user ->
                    if (user != null) {
                        userId=user.id
                    }
                }
        }
    }
}
