package com.berlin.repository.datasource.remote.dto.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosResponse(

	@SerialName("id")
	val id: Int? = null,

	@SerialName("results")
	val results: List<VideoDto?>? = null
)

@Serializable
data class VideoDto(

	@SerialName("id")
	val id: String? = null,

	@SerialName("site")
	val site: String? = null,

	@SerialName("size")
	val size: Int? = null,

	@SerialName("iso_3166_1")
	val iso31661: String? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("official")
	val official: Boolean? = null,

	@SerialName("type")
	val type: String? = null,

	@SerialName("published_at")
	val publishedAt: String? = null,

	@SerialName("iso_639_1")
	val language: String? = null,

	@SerialName("key")
	val key: String? = null
)