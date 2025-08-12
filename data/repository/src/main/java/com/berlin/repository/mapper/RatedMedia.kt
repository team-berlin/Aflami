package com.berlin.repository.mapper

import com.berlin.entity.RatedMedia
import com.berlin.repository.datasource.remote.dto.rating.RatedMediaDto
import com.berlin.repository.util.MediaUrls
import com.berlin.repository.util.tmdbImageUrl

fun RatedMediaDto.toDomain(): RatedMedia {
    return RatedMedia(
        id = id.toLong(),
        title = title ?: name.orEmpty(),
        posterUrl = tmdbImageUrl(posterPath, MediaUrls.TmdbImageSize.W500),
        userRating = userRating,
        voteAverage = voteAverage,
        mediaType = mediaType
    )
}