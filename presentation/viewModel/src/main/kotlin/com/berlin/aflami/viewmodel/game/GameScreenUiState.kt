package com.berlin.aflami.viewmodel.game

data class GameScreenUiState(
    val selectedGameType: GameType,
    val selectedLevel:GameLevel,
    val gameLevel: List<GameLevel>,
    val gameCard:List<GameCard>,
    val points:Int,
    val showDialog:Boolean,
    val isLoading:Boolean
)

data class GameCard(
    val title:String,
    val description:String,
    val isAvailable:Boolean,
    val image:String,
)

data class GameLevel(
    val title:String,
    val numberOfQuestions:Int,
    val points: Int,
    val time:Int
)


enum class GameType{

}
