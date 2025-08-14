package com.berlin.aflami.viewmodel.game

import com.berlin.viewModel.R

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

enum class GameType (val type:Int){
    CHARACTER(R.string.game_guess_character_title),
    POSTER(R.string.game_guess_poster_title),
    RELEASE(R.string.game_release_title),
    GENRE(R.string.game_genre_title)
}

enum class GameLevelType {
    EASY,
    MEDIUM,
    HARD
}


