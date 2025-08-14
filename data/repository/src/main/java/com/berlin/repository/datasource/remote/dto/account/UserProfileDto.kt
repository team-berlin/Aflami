package com.berlin.repository.datasource.remote.dto.account


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    @SerialName("avatar")
    val avatar: Avatar?,
    @SerialName("id")
    val id: Int?,
    @SerialName("include_adult")
    val includeAdult: Boolean?,
    @SerialName("name")
    val name: String?,
    @SerialName("username")
    val username: String?
)