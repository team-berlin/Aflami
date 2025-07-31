package com.berlin.repository.datasource.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageDto(
    @SerialName("english_name")
    val englishName: String?,
    @SerialName("iso_639_1")
    val isoFormat: String?,
    @SerialName("name")
    val name: String?
)