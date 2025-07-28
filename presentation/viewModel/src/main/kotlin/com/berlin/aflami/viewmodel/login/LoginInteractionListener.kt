package com.berlin.aflami.viewmodel.login

interface LoginInteractionListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onTrailingIconClicked()
    fun onForgotPasswordClicked()
    fun onLoginClicked()
    fun onContinueAsGuestClicked()
    fun onCreateAccountClicked()
}