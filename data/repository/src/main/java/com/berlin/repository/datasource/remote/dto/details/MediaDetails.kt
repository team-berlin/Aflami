package com.berlin.repository.datasource.remote.dto.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsDto(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int? = null,
    @SerialName("origin_country")
    val originCountry: List<String?>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("seasons")
    val seasons: List<Season?>? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
)