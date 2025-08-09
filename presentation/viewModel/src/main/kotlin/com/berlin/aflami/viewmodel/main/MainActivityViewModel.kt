package com.berlin.aflami.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatusUseCase
import usecase.onboarding.GetFirstEntryUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isLoggedInUseCase: GetLoginStatusUseCase,
    private val getFirstEntryUseCase: GetFirstEntryUseCase,

    ) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val isFirstEntry = getFirstEntryUseCase()
            val isLoggedIn = isLoggedInUseCase()
            _state.value = MainUiState(
                isLoading = false,
                isFirstEntry = isFirstEntry,
                isLoggedIn = isLoggedIn
            )
        }
    }
}