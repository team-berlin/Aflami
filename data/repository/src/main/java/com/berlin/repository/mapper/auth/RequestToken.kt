package com.berlin.repository.mapper.auth

import com.berlin.entity.auth.RequestToken
import com.berlin.repository.datasource.remote.dto.auth.RequestTokenDTO

fun RequestTokenDTO.toDomain() : RequestToken{
    return RequestToken(
        requestToken = this.requestToken
    )
}