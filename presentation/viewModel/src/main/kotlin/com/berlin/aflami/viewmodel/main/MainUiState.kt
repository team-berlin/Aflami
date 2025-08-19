package com.berlin.aflami.viewmodel.main

import java.util.Locale

data class MainUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isFirstEntry: Boolean = false,
    val isDarkThemeEnabled: Boolean = true,
    val selectedLanguage: String = Locale.getDefault().language.uppercase(),
)
