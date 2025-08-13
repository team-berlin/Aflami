package com.berlin.repository.datasource.remote.response.rating

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitRatingResponse(
    @SerialName("status_code") val statusCode: Int?,
    @SerialName("status_message") val statusMessage: String?
)