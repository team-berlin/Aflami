package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
	@SerialName("id")
	val id: Int? = null,

	@SerialName("overview")
	val overview: String? = null,

	@SerialName("original_language")
	val originalLanguage: String? = null,

	@SerialName("original_title")
	val originalTitle: String? = null,

	@SerialName("video")
	val video: Boolean? = null,

	@SerialName("title")
	val title: String? = null,

	@SerialName("genre_ids")
	val genreIds: List<Int?>? = null,

	@SerialName("poster_path")
	val posterPath: String? = null,

	@SerialName("backdrop_path")
	val backdropPath: String? = null,

	@SerialName("release_date")
	val releaseDate: String? = null,

	@SerialName("popularity")
	val popularity: Double? = null,

	@SerialName("vote_average")
	val voteAverage: Double? = null,

	@SerialName("adult")
	val adult: Boolean? = null,

	@SerialName("vote_count")
	val voteCount: Int? = null
)