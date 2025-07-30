package com.berlin.aflami.viewmodel.login

import androidx.compose.ui.text.input.TextFieldValue

interface LoginInteractionListener {
    fun onUsernameChanged(username: TextFieldValue)
    fun onPasswordChanged(password: TextFieldValue)
    fun onTrailingIconClicked()
    fun onForgotPasswordClicked()
    fun onLoginClicked()
    fun onContinueAsGuestClicked()
    fun onCreateAccountClicked()
}