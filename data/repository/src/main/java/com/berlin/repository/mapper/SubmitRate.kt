package com.berlin.repository.mapper

import com.berlin.entity.RatingResult
import com.berlin.repository.datasource.remote.response.rating.SubmitRatingResponse

fun SubmitRatingResponse.toDomain(): RatingResult {
    return RatingResult(
        statusCode = this.statusCode ?: -1,
        statusMessage = this.statusMessage ?: "Unknown error"
    )
}