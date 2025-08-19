package com.berlin.aflami.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.entity.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.auth.GetLoginUseCase
import usecase.onboarding.GetFirstEntryUseCase
import usecase.profile.GetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isLoggedInUseCase: GetLoginUseCase,
    private val getFirstEntryUseCase: GetFirstEntryUseCase,
    private val getThemeUseCase: GetThemeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val isFirstEntry = getFirstEntryUseCase()

            getThemeUseCase().onEach { theme ->
                _state.value = _state.value.copy(isDark = theme == AppTheme.DARK.name)
            }.launchIn(viewModelScope)


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
