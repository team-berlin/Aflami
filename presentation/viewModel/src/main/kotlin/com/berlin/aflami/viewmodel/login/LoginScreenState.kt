package com.berlin.aflami.viewmodel.login

import androidx.compose.ui.text.input.TextFieldValue
import com.berlin.aflami.viewmodel.base.ErrorUiState

data class LoginScreenState(
    val formUiState: FormUiState = FormUiState(),
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorType: ErrorUiState? = null,
    val success: Boolean = false
)

data class FormUiState(
    val username: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue(""),
    val isPasswordObscured: Boolean = true
)