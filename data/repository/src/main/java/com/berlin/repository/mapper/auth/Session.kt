package com.berlin.repository.mapper.auth

import com.berlin.entity.auth.Session
import com.berlin.repository.datasource.remote.dto.auth.SessionDto

fun SessionDto.toDomain(): Session {
    return Session(
        success = this.success == true,
        sessionId = this.sessionId?:"anysessionid"
    )
}