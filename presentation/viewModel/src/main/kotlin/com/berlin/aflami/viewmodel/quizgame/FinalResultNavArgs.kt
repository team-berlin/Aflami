package com.berlin.aflami.viewmodel.quizgame


import androidx.lifecycle.SavedStateHandle
import javax.inject.Inject

class FinalResultNavArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val totalPoint:Int?=savedStateHandle.get<Int>(TOTAL_POINT)
    val remainingTime:Int?=savedStateHandle.get<Int>(REMAINING_TIME)

    companion object{
        const val TOTAL_POINT="numberOfPoint"
        const val REMAINING_TIME="time"
    }
}