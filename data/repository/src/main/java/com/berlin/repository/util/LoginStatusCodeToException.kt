package com.berlin.repository.util

import com.berlin.entity.InvalidLoginApiKeyException
import com.berlin.entity.InvalidLoginTokenException
import com.berlin.entity.InvalidUsernameOrPasswordException

fun Int.toException() = when (this) {
    Int.Companion.invalidToken -> InvalidLoginTokenException("Invalid request token")
    Int.Companion.invalidUsernameOrPassword -> InvalidUsernameOrPasswordException("Invalid username or password")
    Int.Companion.invalidApiKey -> InvalidLoginApiKeyException("Invalid login api key")
    else -> Exception("Unknown error $this")
}
