package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    @SerialName("adult")
val adult: String? = null,
    @SerialName("gender")
val gender: Int? = null,
    @SerialName("id")
val id: Int? = null,
    @SerialName("known_for_department")
val knownForDepartment: String? = null,
    @SerialName("name")
val name: Boolean? = null,
    @SerialName("original_name")
val originalName: String? = null,
    @SerialName("popularity")
val popularity: Float? = null,
    @SerialName("profile_path")
val profilePath: String? = null,
    @SerialName("known_for")
val knownFor: List<MediaItem> ? = null,
)

