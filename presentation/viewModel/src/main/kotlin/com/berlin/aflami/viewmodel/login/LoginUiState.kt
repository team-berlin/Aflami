package com.berlin.aflami.viewmodel.login

import androidx.compose.ui.text.input.TextFieldValue

data class LoginUiState(
    val formUiState: FormUiState = FormUiState(),
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage:String? = null
)

data class FormUiState(
    val username: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue(""),
    val isPasswordObscured: Boolean = true
)