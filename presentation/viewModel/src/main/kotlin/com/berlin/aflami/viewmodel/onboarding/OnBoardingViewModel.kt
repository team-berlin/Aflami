package com.berlin.aflami.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecase.onboarding.GetFirstEntryUseCase
import usecase.onboarding.SaveFirstEntryUseCase
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val isFirstEntryUseCase: GetFirstEntryUseCase,
    private val saveFirstEntryUseCase: SaveFirstEntryUseCase,
) : ViewModel() {

    private val _isFirstEntry = MutableStateFlow<Boolean?>(null)
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
            _isFirstEntry.value = false
            _effect.emit(OnBoardingScreenEffect.NavigateToLogin)
        }
    }
}
