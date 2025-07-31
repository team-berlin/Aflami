package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id")
    val id: String? = null,

    @SerialName("author")
    val author: String? = null,

    @SerialName("content")
    val content: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("author_details")
    val authorDetailsDto: AuthorDetailsDto? = null
)

