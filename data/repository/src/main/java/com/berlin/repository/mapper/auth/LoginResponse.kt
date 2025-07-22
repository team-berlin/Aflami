package com.berlin.repository.mapper.auth

import com.berlin.entity.auth.LoginToken
import com.berlin.repository.datasource.remote.dto.auth.LoginDto

fun LoginDto.toDomain(): LoginToken{
    return LoginToken(
        success = this.success,
        expiresAt = this.expiresAt,
        requestToken = this.requestToken
    )
}