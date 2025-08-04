package com.berlin.aflami.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecase.onboarding.IsFirstEntryUseCase
import usecase.onboarding.SaveFirstEntryUseCase

class OnBoardingViewModel(
    private val isFirstEntryUseCase: IsFirstEntryUseCase,
    private val saveFirstEntryUseCase: SaveFirstEntryUseCase,
) : ViewModel() {

    private val _isFirstEntry = MutableStateFlow(true)
    val isFirstEntry = _isFirstEntry.asStateFlow()

    protected val _effect = MutableSharedFlow<OnBoardingScreenEffect>()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            _isFirstEntry.value = isFirstEntryUseCase()
        }
    }

    fun saveFirstEntry() {
        viewModelScope.launch {
            saveFirstEntryUseCase()
        }
    }

    fun onClickSkip() {
        viewModelScope.launch {
            _isFirstEntry.value = true
            _effect.emit(OnBoardingScreenEffect.NavigateToLogin)

        }
    }
}