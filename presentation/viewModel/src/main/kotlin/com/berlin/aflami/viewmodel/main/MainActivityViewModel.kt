package com.berlin.aflami.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.onboarding.GetFirstEntryUseCase
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
    val isLoggedInState: StateFlow<Boolean> = isLoggedInUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    var isFirstEntry by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            isFirstEntry = getFirstEntryUseCase()
            isLoading = false
            isLoggedInState.collect { loggedIn ->
                loginState = loggedIn

            }
        }
    }
}