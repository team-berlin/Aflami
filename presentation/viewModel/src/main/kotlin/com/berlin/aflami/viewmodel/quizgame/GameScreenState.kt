package com.berlin.aflami.viewmodel.game

data class GameScreenState(
    val selectedGameType: GameType? = null,
    val selectedLevel: GameLevel? = null,
    val gameLevel: List<GameLevel> = emptyList(),
    val points: Int = 0,
    val showDialog: Boolean = false,
    val isLoading: Boolean = false
)

data class GameLevel(
    val numberOfQuestions: Int,
    val points: Int,
    val type: GameLevelType,
    val time: Int
)

enum class GameType {
    CHARACTER,
    POSTER,
    RELEASE,
    GENRE
}

enum class GameLevelType {
    EASY,
    MEDIUM,
    HARD
}


