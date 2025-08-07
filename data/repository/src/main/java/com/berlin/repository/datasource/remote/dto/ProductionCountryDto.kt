package com.berlin.repository.datasource.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryDto(
    @SerialName("iso_3166_1")
    val isoFormat: String?,
    @SerialName("name")
    val name: String?
)