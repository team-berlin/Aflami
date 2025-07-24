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
        TODO("Not yet implemented")
    }

    override fun onLoginClicked(username: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun onContinueAsGuestClicked() {
        TODO("Not yet implemented")
    }

    override fun onCreateAccountClicked() {
        TODO("Not yet implemented")
    }

}