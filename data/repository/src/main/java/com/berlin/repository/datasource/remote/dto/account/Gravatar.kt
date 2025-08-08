package com.berlin.repository.datasource.remote.dto.account


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gravatar(
    @SerialName("hash")
    val hash: String?
)