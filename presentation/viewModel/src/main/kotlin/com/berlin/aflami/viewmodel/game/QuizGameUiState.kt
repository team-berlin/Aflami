package com.berlin.aflami.viewmodel.game

data class QuizGameUiState(
    val questions: List<Question>,
    val type: QuestionType,
    val currentQuestionIndex: Int,
    val isFinished: Boolean,
    val time:Int,
    val selectedAnswer:String,
    val totalPoint:Int,
    val remainingTime:Int,
    val enableHint:Boolean
)

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
)

enum class QuestionType{
    Image,
    Text
}