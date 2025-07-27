package com.berlin.aflami.viewmodel.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.auth.IsLoggedInUseCase

class MainViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(false)
    val state = _state

    init {
        viewModelScope.launch {
            _state.update {
                isLoggedInUseCase()
            }
        }
    }
}