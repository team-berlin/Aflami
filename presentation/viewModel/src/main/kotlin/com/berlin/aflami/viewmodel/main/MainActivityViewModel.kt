package com.berlin.aflami.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.auth.GetLoginUseCase
import usecase.onboarding.GetFirstEntryUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isLoggedInUseCase: GetLoginUseCase,
    private val getFirstEntryUseCase: GetFirstEntryUseCase,

    ) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState(isLoading = true))
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            val isFirstEntry = getFirstEntryUseCase()

            isLoggedInUseCase().collect { loggedIn ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isFirstEntry = isFirstEntry,
                        isLoggedIn = loggedIn
                    )
                }
            }
        }
    }
}