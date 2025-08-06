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
    private val saveFirstEntryUseCase: SaveFirstEntryUseCase,
) : ViewModel() {


    protected val _effect = MutableSharedFlow<OnBoardingScreenEffect>()
    val effect = _effect.asSharedFlow()


    fun saveFirstEntry() {
        viewModelScope.launch {
            saveFirstEntryUseCase()
        }
    }

    fun onClickSkip() {
        viewModelScope.launch {
            _effect.emit(OnBoardingScreenEffect.NavigateToLogin)
        }
    }
}
