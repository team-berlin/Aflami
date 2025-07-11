package com.berlin.repository.datasource.remote.dto

import com.berlin.entity.MediaTypeEntity
import com.berlin.repository.datasource.local.model.SearchCaching


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
data class MediaItem(
    @SerialName("id")
    val id: Long,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?=null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? =null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,

    )
fun MediaItem.toEntity() : MediaTypeEntity {
    return when (this.mediaType) {
        "movie" -> MediaTypeEntity(
            id = this.id,
            title = this.title?:"",
            rating = this.voteAverage?: 0.0,
            mediaType = this.mediaType?: "",
            releaseYear = LocalDate.parse(this.releaseDate?:"") ,
            genre = this.genreIds?:emptyList(),
            poster =this.posterPath?: ""
        )
        "tv" -> MediaTypeEntity(
            id = this.id,
            title = this.name?: "",
            rating = this.voteAverage?: 0.0,
            mediaType = this.mediaType?: "",
            releaseYear = LocalDate.parse(this.firstAirDate?:"") ,
            genre = this.genreIds?:emptyList(),
            poster =this.posterPath?: ""
        )

        else -> {
            MediaTypeEntity(
                id = this.id,
                title = this.title?: "",
                rating = this.voteAverage?: 0.0,
                mediaType = this.mediaType?: "",
                releaseYear = LocalDate.parse(this.firstAirDate?: "") ,
                genre = this.genreIds?:emptyList(),
                poster =this.posterPath?: ""
            )
        }
    }
}
fun MediaItem.toLocal(query: String,type: String): SearchCaching{
    return SearchCaching(
        query = query,
        type = type,
        time = System.currentTimeMillis(),
        id = id,
        title = title?:name?:"",
        rating = voteAverage?:0.0,
        releaseYear = releaseDate?:firstAirDate?:"",
        description = "",
        poster = posterPath?:"",
        mediaType = mediaType?:""
    )
}
