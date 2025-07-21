package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class SessionResponse {

    @SerialName("success")
    val success: Boolean? = null
    @SerialName("session_id")
    val sessionId: String? = null

}