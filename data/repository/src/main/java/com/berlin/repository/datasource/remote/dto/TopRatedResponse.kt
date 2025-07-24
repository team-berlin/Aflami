package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class TopRatedResponse(
    @SerialName("results")
    val topRatedMovies: List<MovieDto>,
    @SerialName("page")
    val page: Int,
)