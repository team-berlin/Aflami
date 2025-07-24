package com.berlin.aflami.viewmodel.login

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.util.SNACK_BAR_DURATION
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import usecase.ValidatePasswordUseCase
import usecase.ValidateUsernameUseCase

class LoginViewmodel(
    val usernameValidationUseCase: ValidateUsernameUseCase,
    val passwordValidationUseCase: ValidatePasswordUseCase
) : BaseViewModel<LoginUiState, LoginEffect>(LoginUiState()),
    LoginInteractionListener {
    override fun onUsernameChanged(username: String) {
        updateState { it.copy(formUiState = it.formUiState.copy(username = username)) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(formUiState = it.formUiState.copy(password = password)) }
    }

    override fun onTrailingIconClicked() {
        updateState { it.copy(formUiState = it.formUiState.copy(isPasswordObscured = !it.formUiState.isPasswordObscured)) }
    }

    override fun onForgotPasswordClicked() {
        sendNewEffect(LoginEffect.NavigateToForgotPassword)
    }

    override fun onLoginClicked() {
        val isValidated =
            usernameValidationUseCase(state.value.formUiState.username) && passwordValidationUseCase(
                state.value.formUiState.password
            )
        if (!isValidated) {
            updateState { it.copy(isError = true) }
            viewModelScope.launch {
                delay(SNACK_BAR_DURATION)
                updateState { it.copy(isError = false) }
            }
            return
        }
        updateState { it.copy(isLoading = true) }
        //call login usecase
    }

    override fun onContinueAsGuestClicked() {
        sendNewEffect(LoginEffect.NavigateToHome)
    }

    override fun onCreateAccountClicked() {
        sendNewEffect(LoginEffect.NavigateToCreateAccount)
    }
}