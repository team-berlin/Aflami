package com.berlin.repository.util

import com.berlin.entity.InvalidLoginApiKeyException
import com.berlin.entity.InvalidLoginTokenException
import com.berlin.entity.InvalidUsernameOrPasswordException

fun String?.toException() = when (this) {
    String.Companion.invalidToken -> InvalidLoginTokenException("Invalid request token")
    String.Companion.invalidUsernameOrPassword -> InvalidUsernameOrPasswordException("Invalid username or password")
    String.Companion.invalidApiKey -> InvalidLoginApiKeyException("Invalid login api key")
    else -> Exception("Unknown error $this")
}
