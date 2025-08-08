package com.berlin.repository.mapper

import com.berlin.entity.RatedMedia
import com.berlin.repository.datasource.remote.dto.rating.RatedMediaDto

fun RatedMediaDto.toDomain(): RatedMedia {
    return RatedMedia(
        id = id,
        title = title ?: name.orEmpty(),
        posterUrl = posterPath,
        userRating = userRating,
        voteAverage = voteAverage
    )
}