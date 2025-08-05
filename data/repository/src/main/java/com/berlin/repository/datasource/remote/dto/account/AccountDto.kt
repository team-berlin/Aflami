package com.berlin.repository.datasource.remote.dto.account


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("avatar")
    val avatar: Avatar?,
    @SerialName("id")
    val id: Int?,
    @SerialName("include_adult")
    val includeAdult: Boolean?,
    @SerialName("iso_3166_1")
    val iso31661: String?,
    @SerialName("iso_639_1")
    val iso6391: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("username")
    val username: String?
)