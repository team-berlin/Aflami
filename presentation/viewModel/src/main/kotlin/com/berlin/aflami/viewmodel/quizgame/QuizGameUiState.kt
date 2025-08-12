package com.berlin.aflami.viewmodel.quizgame

import com.berlin.aflami.viewmodel.base.ErrorUiState

data class QuizGameUiState(
    val questions: List<Question> = emptyList(),
    val type: QuestionType=QuestionType.Image,
    val selectedAnswer:String="",
    val isAnswerCorrect:Boolean?=null,
    val imageBlur:Int=0,
    val currentQuestionIndex: Int=0,
    val isFinished: Boolean=false,
    val time:Int=0,
    val totalPoint:Int=0,
    val remainingTime:Int=0,
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