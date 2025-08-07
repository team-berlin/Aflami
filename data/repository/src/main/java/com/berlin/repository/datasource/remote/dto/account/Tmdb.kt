package com.berlin.repository.datasource.remote.dto.account


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tmdb(
    @SerialName("avatar_path")
    val avatarPath: String?
)