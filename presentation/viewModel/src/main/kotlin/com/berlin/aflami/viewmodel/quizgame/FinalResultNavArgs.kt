package com.berlin.aflami.viewmodel.quizgame


import androidx.lifecycle.SavedStateHandle
import javax.inject.Inject

class FinalResultNavArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val gameType:String?= savedStateHandle.get<String>(GAME_TYPE)
    val numberOfQuestion:Int?= savedStateHandle.get<Int>(NUMBER_OF_QUESTION)
    val numberOfPoint:Int?=savedStateHandle.get<Int>(NUMBER_OF_POINT)
    val timer:Int?=savedStateHandle.get<Int>(TIMER)
    val totalPoint=savedStateHandle.get<Int>(TOTAL_POINT)
    val totalTime=savedStateHandle.get<Int>(TOTAL_TIME)

    companion object{
        const val GAME_TYPE="gameType"
        const val NUMBER_OF_QUESTION="numberOfQuestion"
        const val NUMBER_OF_POINT="numberOfPoint"
        const val TIMER="time"
        const val TOTAL_POINT="total_point"
        const val TOTAL_TIME="total_time"
    }
}