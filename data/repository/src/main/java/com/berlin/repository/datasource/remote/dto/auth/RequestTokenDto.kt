package com.berlin.repository.datasource.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenDTO(
    @SerialName("request_token")
    val requestToken: String
)

