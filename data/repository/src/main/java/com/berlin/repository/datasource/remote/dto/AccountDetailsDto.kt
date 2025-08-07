package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDetailsDto(
    @SerialName("id")
    val accountId: Int,

    @SerialName("name")
    val name: String,

    @SerialName("username")
    val username: String,

    @SerialName("include_adult")
    val includeAdult: Boolean,

    @SerialName("iso_639_1")
    val iso6391: String,

    @SerialName("iso_3166_1")
    val iso31661: String,
)