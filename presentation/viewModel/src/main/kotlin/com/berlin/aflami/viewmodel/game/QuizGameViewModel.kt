package com.berlin.aflami.viewmodel.game

import com.berlin.aflami.viewmodel.base.BaseViewModel
import javax.inject.Inject

class QuizGameViewModel @Inject constructor(

): BaseViewModel<QuizGameUiState,QuizGameEffect>(QuizGameUiState()) {

}