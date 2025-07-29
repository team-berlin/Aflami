package com.berlin.aflami.viewmodel.login

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.util.SNACK_BAR_DURATION
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import usecase.ValidatePasswordUseCase
import usecase.ValidateUsernameUseCase
import usecase.auth.LoginUseCase

class LoginViewmodel(
    val usernameValidationUseCase: ValidateUsernameUseCase,
    val passwordValidationUseCase: ValidatePasswordUseCase,
    val loginUseCase: LoginUseCase,

    ) : BaseViewModel<LoginUiState, LoginEffect>(LoginUiState()),
    LoginInteractionListener {
    override fun onUsernameChanged(username: CharSequence) {
        updateState {
            it.copy(
                formUiState = it.formUiState.copy(username = username.toString()),
                isLoginButtonEnabled = username.isNotBlank() && it.formUiState.password.isNotBlank()
            )
        }
    }

    override fun onPasswordChanged(password: String) {
        updateState {
            it.copy(
                formUiState = it.formUiState.copy(password = password),
                isLoginButtonEnabled = password.isNotBlank() && it.formUiState.username.isNotBlank()
            )
        }
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
            handleErrorState()
            return
        }
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                loginUseCase(state.value.formUiState.username, state.value.formUiState.password)
                updateState { it.copy(isLoading = false) }
                sendNewEffect(newEffect = LoginEffect.NavigateToHome)
            } catch (e: Exception) {
                handleErrorState()
            }
        }
    }

    override fun onContinueAsGuestClicked() {
        sendNewEffect(LoginEffect.NavigateToHome)
    }

    override fun onCreateAccountClicked() {
        sendNewEffect(LoginEffect.NavigateToCreateAccount)
    }

    private fun handleErrorState() {
        updateState { it.copy(isError = true) }
        viewModelScope.launch {
            delay(SNACK_BAR_DURATION)
            updateState {
                it.copy(
                    isError = false,
                    isLoginButtonEnabled = false,
                    isLoading = false
                )
            }
        }
    }
}