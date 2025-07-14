package com.berlin.repository.mapper

import com.berlin.entity.Review
import com.berlin.repository.datasource.remote.dto.ReviewDto
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ReviewDto.toDomain(): Review {
    return Review(
        id = this.id?.toLong() ?: 0L,
        name = this.author ?: this.authorDetails?.name ?: "",
        userName = this.authorDetails?.userName ?: "",
        avatarImage = this.authorDetails?.avatarPath ?: "",
        rating = this.authorDetails?.rating ?: 0.0,
        content = this.content ?: "",
        date = (this.createdAt ?: Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date) as LocalDate,
    )
}