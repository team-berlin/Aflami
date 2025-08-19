package com.berlin.aflami.viewmodel.login

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import usecase.auth.GetValidatePasswordUseCase
import usecase.auth.GetValidateUsernameUseCase
import usecase.auth.LoginUseCase
import usecase.profile.GetUserProfileUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    val usernameValidationUseCase: GetValidateUsernameUseCase,
    val passwordValidationUseCase: GetValidatePasswordUseCase,
    val loginUseCase: LoginUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : BaseViewModel<LoginScreenState, LoginScreenEffect>(LoginScreenState()),
    LoginInteractionListener {

    override fun onUsernameChanged(username: TextFieldValue) {
        updateState { screenState ->
            screenState.copy(
                formUiState = screenState.formUiState.copy(username = username),
                isLoginButtonEnabled = username.text.isNotBlank() && screenState.formUiState.password.text.isNotBlank()
            )
        }
    }

    override fun onPasswordChanged(password: TextFieldValue) {
        updateState { screenState ->
            screenState.copy(
                formUiState = screenState.formUiState.copy(password = password),
                isLoginButtonEnabled = password.text.isNotBlank() && screenState.formUiState.username.text.isNotBlank()
            )
        }
    }

    override fun onTrailingIconClicked() {
        updateState { screenState ->
            screenState.copy(
                formUiState = screenState.formUiState.copy(
                    isPasswordObscured = !screenState.formUiState.isPasswordObscured
                )
            )
        }
    }

    override fun onForgotPasswordClicked() =
        sendNewEffect(LoginScreenEffect.NavigateToForgotPassword)

    override fun onLoginClicked() {
        val isValidated =
            usernameValidationUseCase(state.value.formUiState.username.text) && passwordValidationUseCase(
                state.value.formUiState.password.text
            )
        if (!isValidated) {
            handleErrorState("Invalid username or password")
            return
        }
        updateState { screenState -> screenState.copy(isLoading = true) }
        tryToCall(
            call = {
                loginUseCase(
                    state.value.formUiState.username.text,
                    state.value.formUiState.password.text
                )
            },
            onSuccess = {
                tryToCall(
                    call = { getUserProfileUseCase() },
                    onSuccess = {
                        updateState { it.copy(isLoading = false) }
                        sendNewEffect(LoginScreenEffect.NavigateToHomeScreen)
                    },
                    onError = { e ->
                        handleErrorState(e.message)
                    }
                )
            },
            onError = { handleErrorState(it.message) },
        )

    }

    override fun onContinueAsGuestClicked() =
        sendNewEffect(LoginScreenEffect.NavigateToHomeScreen)

    override fun onCreateAccountClicked() =
        sendNewEffect(LoginScreenEffect.NavigateToCreateAccountScreen)

    private fun handleErrorState(message: String?) {
        updateState { screenState ->
            screenState.copy(
                isError = true,
                errorMessage = message
            )
        }
        viewModelScope.launch {
            delay(SNACK_BAR_DURATION)
            updateState { screenState ->
                screenState.copy(
                    isError = false,
                    isLoginButtonEnabled = false,
                    isLoading = false
                )
            }
        }
    }
    companion object{
        const val SNACK_BAR_DURATION = 3000L
    }
}