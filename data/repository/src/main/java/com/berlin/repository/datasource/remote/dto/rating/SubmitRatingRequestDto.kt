package com.berlin.repository.datasource.remote.dto.rating

import kotlinx.serialization.Serializable

@Serializable
data class SubmitRatingRequestDto(
    val value: Double
)
