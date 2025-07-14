package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PersonDto(
	@SerialName("gender")
	val gender: Int? = null,

	@SerialName("known_for_department")
	val knownForDepartment: String? = null,

	@SerialName("original_name")
	val originalName: String? = null,

	@SerialName("popularity")
	val popularity: Double? = null,

	@SerialName("known_for")
	val knownFor: List<MovieDto?>? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("profile_path")
	val profilePath: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("adult")
	val adult: Boolean? = null
)