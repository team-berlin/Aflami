package com.berlin.aflami.viewmodel.game

sealed interface QuizGameEffect {
    object CloseGameClicked:QuizGameEffect,
    object NextQuestionClicked:QuizGameEffect
}