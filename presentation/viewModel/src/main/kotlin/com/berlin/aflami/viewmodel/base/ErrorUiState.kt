package com.berlin.aflami.viewmodel.base

open class ErrorUiState(val message: String? = null)
class NullResultErrorState(message: String) : ErrorUiState(message)
class InvalidationErrorState(message: String) : ErrorUiState(message)
class NetworkErrorState(message: String) : ErrorUiState(message)
class MovieAlreadyExistInList(message: String) : ErrorUiState(message)