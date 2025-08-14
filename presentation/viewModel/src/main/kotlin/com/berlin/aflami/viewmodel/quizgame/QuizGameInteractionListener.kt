package com.berlin.aflami.viewmodel.quizgame

interface QuizGameInteractionListener {
    fun nextQuestionClicked()
    fun answerClicked(answer: String)
    fun hintClicked()
    fun closeGameClicked()
    fun navigateToResult()
    fun onDismissLevelDialog()
    fun updateRemainingTime(remainingTime:Int)
}