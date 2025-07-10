package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    @SerialName("adult")
val overview: String? = null,
    @SerialName("gender")
val originalLanguage: Int? = null,
    @SerialName("id")
val id: Int? = null,
    @SerialName("known_for_department")
val originalTitle: String? = null,
    @SerialName("name")
val video: Boolean? = null,
    @SerialName("original_name")
val title: String? = null,
    @SerialName("popularity")
val genreIds: Float? = null,
    @SerialName("profile_path")
val releaseDate: String? = null,
    @SerialName("known_for")
val popularity: List<MediaDto> ? = null,
)

