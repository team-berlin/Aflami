package com.berlin.aflami.viewmodel.game

data class QuizGameUiState(
    val questions: List<Question> = emptyList(),
    val type: QuestionType=QuestionType.Image,
    val currentQuestionIndex: Int=0,
    val isFinished: Boolean=false,
    val time:Int=0,
    val selectedAnswer:String="",
    val totalPoint:Int=0,
    val remainingTime:Int=0,
    val enableHint:Boolean=false
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