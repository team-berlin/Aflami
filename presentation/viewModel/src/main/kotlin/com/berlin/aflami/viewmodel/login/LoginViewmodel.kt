package com.berlin.aflami.viewmodel.login

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.util.SNACK_BAR_DURATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import usecase.ValidatePasswordUseCase
import usecase.ValidateUsernameUseCase
import usecase.auth.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor (
    val usernameValidationUseCase: ValidateUsernameUseCase,
    val passwordValidationUseCase: ValidatePasswordUseCase,
    val loginUseCase: LoginUseCase,

    ) : BaseViewModel<LoginUiState, LoginEffect>(LoginUiState()),
    LoginInteractionListener {
    override fun onUsernameChanged(username: TextFieldValue) {
        updateState {
            it.copy(
                formUiState = it.formUiState.copy(username = username),
                isLoginButtonEnabled = username.text.isNotBlank() && it.formUiState.password.text.isNotBlank()
            )
        }
    }

    override fun onPasswordChanged(password: TextFieldValue) {
        updateState {
            it.copy(
                formUiState = it.formUiState.copy(password = password),
                isLoginButtonEnabled = password.text.isNotBlank() && it.formUiState.username.text.isNotBlank()
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
            usernameValidationUseCase(state.value.formUiState.username.text) && passwordValidationUseCase(
                state.value.formUiState.password.text
            )
        if (!isValidated) {
            handleErrorState("Invalid username or password")
            return
        }
        updateState { it.copy(isLoading = true) }
        tryToCall(
            call = {
                loginUseCase(
                    state.value.formUiState.username.text,
                    state.value.formUiState.password.text
                )
            },
            onSuccess = {
                updateState { it.copy(isLoading = false) }
                sendNewEffect(newEffect = LoginEffect.NavigateToHome)
            },
            onError = { handleErrorState(it.message) },
        )

    }

    override fun onContinueAsGuestClicked() {
        sendNewEffect(LoginEffect.NavigateToHome)
    }

    override fun onCreateAccountClicked() {
        sendNewEffect(LoginEffect.NavigateToCreateAccount)
    }

    private fun handleErrorState(message: String) {
        updateState {
            it.copy(
                isError = true,
                errorMessage = message
            )
        }
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