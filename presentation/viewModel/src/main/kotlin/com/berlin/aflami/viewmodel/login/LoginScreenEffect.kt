package com.berlin.aflami.viewmodel.login

sealed class LoginScreenEffect {
    object NavigateToForgotPassword : LoginScreenEffect()
    data class NavigateToHomeScreen(val isLoggedIn: Boolean = false) : LoginScreenEffect()
    object NavigateToHomeScreenAsGuest : LoginScreenEffect()
    object NavigateToCreateAccountScreen : LoginScreenEffect()
}