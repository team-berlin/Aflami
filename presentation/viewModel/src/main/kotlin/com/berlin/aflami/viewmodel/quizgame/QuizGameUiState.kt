package com.berlin.aflami.viewmodel.quizgame

import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.game.GameType

data class QuizGameUiState(
    val questions: List<Question> = emptyList(),
    val type: QuestionType=QuestionType.Image,
    val gameTypeName:GameType=GameType.GENRE,
    val selectedAnswer:String="",
    val showDialog:Boolean=false,
    val isAnswerCorrect:Boolean?=null,
    val imageBlur:Float=8f,
    val currentQuestionIndex: Int=0,
    val time:Int=0,
    val totalPoint:Int=0,
    val numberOfPoint:Int=0,
    val remainingTime:Int=0,
    val totalRemainingTime:Int=0,
    val enableHint:Boolean=false,
    val loading:Boolean=false,
    val error:ErrorUiState?=null
)

data class Question(
    val question: String="",
    val options: List<String> = emptyList(),
    val correctAnswer: String ="",
)

enum class QuestionType{
    Image,
    Text
}

