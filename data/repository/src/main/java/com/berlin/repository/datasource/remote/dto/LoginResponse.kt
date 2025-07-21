package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (

    @SerialName("success")
    val success: Boolean,
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("request_token")
    val requestToken: String,
)

data class LoginRequestDTO(
    val userName: String,
    val password: String,
    val requestToken: String? = null
)
data class RequestTokenRequestDTO(
    val requestToken: String
)
