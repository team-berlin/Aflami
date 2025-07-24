package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRatedMoviesResponse(
    @SerialName("results")
    val topRatedMovies: List<MovieDto>,
    @SerialName("page")
    val page: Int,
)

@Serializable
data class TopRatedSeriesResponse(
    @SerialName("results")
    val topRatedSeries: List<TVShowDto>,
    @SerialName("page")
    val page: Int,
)