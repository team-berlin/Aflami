package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("author")
    val author: String? = null,

    @SerialName("content")
    val content: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("author_details")
    val authorDetails: AuthorDetails? = null
)

@Serializable
data class AuthorDetails(

    @SerialName("name")
    val name: String? = null,

    @SerialName("username")
    val userName: String? = null,

    @SerialName("avatar_path")
    val avatarPath: String? = null,

    @SerialName("rating")
    val rating: Double? = null
)
