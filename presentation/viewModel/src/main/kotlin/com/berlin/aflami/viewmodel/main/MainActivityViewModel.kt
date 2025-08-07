package com.berlin.aflami.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.onboarding.GetFirstEntryUseCase
import usecase.onboarding.SaveFirstEntryUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isLoggedInUseCase: GetLoginStatus,
    private val getFirstEntryUseCase: GetFirstEntryUseCase,

    ) : ViewModel() {
    var isLoading by mutableStateOf(true)
        private set
    var loginState by mutableStateOf(false)
        private set
    var isFirstEntry by mutableStateOf(false)
        private set
    init {
        viewModelScope.launch {
            isFirstEntry = getFirstEntryUseCase()
            loginState = isLoggedInUseCase()
            isLoading = false
        }
    }
}