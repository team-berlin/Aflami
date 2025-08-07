package com.berlin.repository.mapper

import com.berlin.entity.Review
import com.berlin.repository.datasource.remote.dto.ReviewDto

fun ReviewDto.toDomain(): Review {
    return Review(
        id = this.id ?: "",
        name = this.author ?: this.authorDetailsDto?.name ?: "",
        userName = this.authorDetailsDto?.userName ?: "",
        avatarImage = "$POSTER_PREFIX${this.authorDetailsDto?.avatarPath}",
        rating = this.authorDetailsDto?.rating ?: 0.0,
        content = this.content ?: "",
        date =   this.createdAt ?:""
    )
}
