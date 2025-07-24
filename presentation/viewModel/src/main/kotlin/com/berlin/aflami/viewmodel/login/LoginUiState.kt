package com.berlin.aflami.viewmodel.login

data class LoginUiState(
    val formUiState: FormUiState = FormUiState(),
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

data class FormUiState(
    val username: String = "",
    val password: String = "",
    val isPasswordObscured: Boolean = true
)