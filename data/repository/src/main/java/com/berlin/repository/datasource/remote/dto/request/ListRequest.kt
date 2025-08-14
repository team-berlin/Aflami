package com.berlin.repository.datasource.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ListRequest(
    val name: String,
    val description: String,
    val language: String = "en",
)
