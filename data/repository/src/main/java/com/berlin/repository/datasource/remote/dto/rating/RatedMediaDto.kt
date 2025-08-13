package com.berlin.repository.datasource.remote.dto.rating

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatedMediaDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String? = null, // for TV shows
    @SerialName("title") val title: String? = null, // for Movies
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("rating") val userRating: Double,
)
