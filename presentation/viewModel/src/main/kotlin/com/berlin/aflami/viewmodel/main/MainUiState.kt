package com.berlin.aflami.viewmodel.main

data class MainUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isFirstEntry: Boolean = false,
)
