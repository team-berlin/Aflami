package com.berlin.aflami.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    val isLoggedInState: StateFlow<Boolean> = isLoggedInUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    init {
        viewModelScope.launch {
            val isFirstEntry = getFirstEntryUseCase()
//            val isLoggedIn: Flow<Boolean> = isLoggedInUseCase()
            isLoggedInState.collect { loggedIn ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isFirstEntry = isFirstEntry,
                        isLoggedIn = loggedIn
                    )
                }
            }
            _state.value = MainUiState(
                isLoading = false,
                isFirstEntry = isFirstEntry,
                isLoggedIn = isLoggedInState.value
            )
        }
    }
}