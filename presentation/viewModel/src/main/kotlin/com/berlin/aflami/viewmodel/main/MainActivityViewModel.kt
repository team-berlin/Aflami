package com.berlin.aflami.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.onboarding.GetFirstEntryUseCase
import usecase.onboarding.SaveFirstEntryUseCase
import javax.inject.Inject

data class MainUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isFirstEntry: Boolean = false
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isLoggedInUseCase: GetLoginStatus,
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