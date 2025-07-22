package com.berlin.repository.datasource.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDTO(
    @SerialName("username")
    val userName: String,
    @SerialName("password")
    val password: String,
    @SerialName("request_token")
    val requestToken: String
)
