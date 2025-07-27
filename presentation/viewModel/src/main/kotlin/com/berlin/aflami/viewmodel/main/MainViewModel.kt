package com.berlin.aflami.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import usecase.auth.IsLoggedInUseCase

class MainViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {

    var loginState by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            loginState = isLoggedInUseCase()
        }
    }
}