package com.berlin.aflami.viewmodel.login

sealed class LoginEffect {
    object NavigateToForgotPassword : LoginEffect()
    object NavigateToHome : LoginEffect()
    object NavigateToCreateAccount : LoginEffect()
}