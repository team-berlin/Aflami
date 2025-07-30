package com.berlin.repository.util

import com.berlin.exception.InvalidLoginApiKeyException
import com.berlin.exception.InvalidLoginTokenException
import com.berlin.exception.InvalidUsernameOrPasswordException
import com.berlin.exception.SessionDeniedException

fun String?.toException() = when (this) {
    String.Companion.invalidToken -> InvalidLoginTokenException("Invalid request token")
    String.Companion.invalidUsernameOrPassword -> InvalidUsernameOrPasswordException("Invalid username or password")
    String.Companion.invalidApiKey -> InvalidLoginApiKeyException("Invalid login api key")
    String.Companion.sessionDenied -> SessionDeniedException("Session Denied")
    else -> Exception("Unknown error $this")
}
