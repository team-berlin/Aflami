package com.berlin.aflami.viewmodel.login

interface LoginInteractionListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onTrailingIconClicked()
    fun onForgotPasswordClicked()
    fun onLoginClicked(username: String, password: String)
    fun onContinueAsGuestClicked()
    fun onCreateAccountClicked()
}