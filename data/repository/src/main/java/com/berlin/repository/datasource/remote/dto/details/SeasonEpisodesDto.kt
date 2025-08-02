package com.berlin.repository.datasource.remote.dto.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonEpisodesDto(
    @SerialName("air_date")
    val airDate: String? = null,
    @SerialName("episodes")
    val episodes: List<EpisodeDto>? = null,
    @SerialName("_id")
    val id: String? = null,
    @SerialName("id")
    val id_Season: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("season_number")
    val seasonNumber: Int? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null
)