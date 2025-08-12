package com.berlin.repository.datasource.remote.dto.rating

import kotlinx.serialization.SerialName

data class RatedMediaDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?, // for TV shows
    @SerialName("title") val title: String?, // for Movies
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("rating") val userRating: Double,
    @SerialName("media_type") val mediaType: String? = null
)
