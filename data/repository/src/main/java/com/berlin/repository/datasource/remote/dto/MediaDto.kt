package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDto(
    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("media_type")
    val mediaType: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,

    @SerialName("popularity")
    val popularity: Double? = null,


    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null
)

@Serializable
sealed class MediaItem {
    abstract val id: Long
    abstract val mediaType: String
    abstract val posterPath: String?
    abstract val overview: String
}
@Serializable
@SerialName("movie")
data class MovieItem(
    override val id: Long,
    @SerialName("media_type") override val mediaType: String,
    @SerialName("poster_path") override val posterPath: String?,
    override val overview: String,
    @SerialName("release_date") val releaseDate: String?
) : MediaItem()

@Serializable
@SerialName("tv")
data class TvItem(
    override val id: Long,
    @SerialName("media_type") override val mediaType: String,
    @SerialName("poster_path") override val posterPath: String?,
    override val overview: String,
    @SerialName("first_air_date") val firstAirDate: String?
) : MediaItem()