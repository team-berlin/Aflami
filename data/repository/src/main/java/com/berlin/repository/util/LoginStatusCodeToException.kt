package com.berlin.repository.util

import com.berlin.entity.InvalidLoginApiKeyException
import com.berlin.entity.InvalidLoginTokenException
import com.berlin.entity.InvalidUsernameOrPasswordException
import com.berlin.entity.SessionDeniedException

fun String?.toException() = when (this) {
    String.Companion.invalidToken -> InvalidLoginTokenException("Invalid request token")
    String.Companion.invalidUsernameOrPassword -> InvalidUsernameOrPasswordException("Invalid username or password")
    String.Companion.invalidApiKey -> InvalidLoginApiKeyException("Invalid login api key")
    String.Companion.sessionDenied -> SessionDeniedException("Session Denied")
    else -> Exception("Unknown error $this")
}
