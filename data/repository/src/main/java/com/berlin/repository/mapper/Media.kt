package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.MediaType
import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.dto.MediaDto
import java.time.Instant

fun MediaDto.toLocal(query: String, type: String, page: Int, mediaType: String?): SearchingEntity {
    return SearchingEntity(
        query = query,
        type = type,
        time = Instant.now().epochSecond,
        id = this.id?.toLong() ?: 0L,
        title = this.title ?: this.name ?: "",
        rating = this.voteAverage ?: 0.0,
        releaseYear = (releaseDate ?: ""),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        page = page,
        mediaType = mediaType ?: ""
    )
}

fun SearchingEntity.toMedia(): Media {
    return Media(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = stringToLocalDate(releaseYear),
        genre = this.genre,
        poster = this.poster,
        mediaType = this.mediaType
    )
}

fun GenreDto.toDomain(): Genre {
    return Genre(id = this.id, name = this.name)
}

fun ContinueWatchingMovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = stringToLocalDate(this.releaseYear),
        genres = emptyList(),
        poster = this.posterUrl,
    )
}

fun ContinueWatchingTVShowEntity.toTVShow(): TVShow {
    return TVShow(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = stringToLocalDate(this.releaseYear),
        genre = emptyList(),
        poster = this.posterUrl,
    )
}

fun Movie.toLocalEntity(): ContinueWatchingMovieEntity {
    return ContinueWatchingMovieEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = this.releaseDate.toString(),
        posterUrl = this.poster,
        typeOfMedia = MediaType.MOVIE
    )
}

fun TVShow.toLocalEntity(): ContinueWatchingTVShowEntity {
    return ContinueWatchingTVShowEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = this.releaseYear.toString(),
        posterUrl = this.poster,
        typeOfMedia = MediaType.TVSHOW
    )
}
