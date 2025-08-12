package com.berlin.aflami.viewmodel.quizgame

sealed interface QuizGameEffect {
    object CloseGameClicked:QuizGameEffect
}