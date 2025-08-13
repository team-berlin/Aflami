package com.berlin.repository.mapper

import com.berlin.entity.RatedMovie
import com.berlin.entity.RatedTVShow
import com.berlin.repository.datasource.remote.dto.rating.RatedMediaDto
import com.berlin.repository.util.MediaUrls
import com.berlin.repository.util.tmdbImageUrl

fun RatedMediaDto.toDomainRatedMovie(): RatedMovie = RatedMovie(
    id = id.toLong(),
    title = title.orEmpty(),
    posterUrl = tmdbImageUrl(posterPath, MediaUrls.TmdbImageSize.W500),
    userRating = userRating,
    voteAverage = voteAverage
)

fun RatedMediaDto.toDomainRatedTVShow(): RatedTVShow = RatedTVShow(
    id = id.toLong(),
    name = name.orEmpty(),
    posterUrl = tmdbImageUrl(posterPath, MediaUrls.TmdbImageSize.W500),
    userRating = userRating,
    voteAverage = voteAverage
)