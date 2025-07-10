package com.berlin.repository.datasource.remote.dto

import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import com.berlin.entity.TvShowEntity

import kotlinx.datetime.LocalDate
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

    // abstract val mediaType: String
    abstract val posterPath: String?
    abstract val overview: String
}

@Serializable
@SerialName("movie")
data class MovieItem(
    override val id: Long,
    // @SerialName("media_type") override val mediaType: String,
    @SerialName("poster_path") override val posterPath: String?,
    override val overview: String,
    @SerialName("release_date") val releaseDate: String?,

    @SerialName("title") val title: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int>? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("video") val video: Boolean? = null
) : MediaItem()

@Serializable
@SerialName("tv")
data class TvItem(
    override val id: Long,
    //@SerialName("media_type") override val mediaType: String,
    @SerialName("poster_path") override val posterPath: String?,
    override val overview: String,
    @SerialName("first_air_date") val firstAirDate: String?,

    @SerialName("name") val name: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int>? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    @SerialName("adult") val adult: Boolean? = null
) : MediaItem()

fun MediaItem.toEntity(): MediaTypeEntity {
    return when (this) {
        is MovieItem -> Movie(
            id = this.id,
            title = this.title ?: "",
            rating = this.voteAverage ?: 0.0,
            releaseYear = LocalDate.parse(releaseDate ?: ""),
            description = this.overview,
            genre = this.genreIds ?: emptyList(),
            poster = this.posterPath ?: "",
        )

        is TvItem -> TvShowEntity(
            id = this.id,
            title = this.name ?: "",
            rating = this.voteAverage ?: 0.0,
            releaseYear = LocalDate.parse(this.firstAirDate ?: ""),
            description = this.overview,
            genre = this.genreIds ?: emptyList(),
            poster = this.posterPath ?: "",
        )
    }
}