package com.berlin.aflami.viewmodel.login

sealed class LoginScreenEffect {
    object NavigateToForgotPassword : LoginScreenEffect()
    object NavigateToHomeScreen : LoginScreenEffect()
    object NavigateToCreateAccountScreen : LoginScreenEffect()
}