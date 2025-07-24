package com.berlin.aflami.viewmodel.login

import com.berlin.aflami.viewmodel.base.BaseViewModel

class LoginViewmodel : BaseViewModel<LoginUiState, LoginEffect>(LoginUiState()),
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

    override fun onLoginClicked(username: String, password: String) {
        //validate username and password
        //call login usecase
    }

    override fun onContinueAsGuestClicked() {
        sendNewEffect(LoginEffect.NavigateToHome)
    }

    override fun onCreateAccountClicked() {
        sendNewEffect(LoginEffect.NavigateToCreateAccount)
    }
}