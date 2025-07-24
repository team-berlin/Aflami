package com.berlin.repository

import android.util.Log
import com.berlin.entity.Media
import com.berlin.repository.MediaType.MOVIE
import com.berlin.repository.MediaType.TV_SHOW
import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.stringToLocalDate
import repository.HomeRepository

class HomeRepositoryImpl(
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    init {
        Log.d("DI", "HomeRepositoryImpl created")
    }
    override suspend fun getPopularMovies(language: String): List<Media> {
        return try {
            return remoteDataSource.getPopularMovies(language).results
                ?.filterNotNull()
                ?.map { movieDto -> movieDto.toDomain(MOVIE) }
                ?: emptyList()
        } catch (e: Exception) {
            Log.e("getPopularMovies", "Error fetching movies", e)
            emptyList()
        }
    }

    override suspend fun getPopularTVShows(language: String): List<Media> {
        return try {
            return remoteDataSource.getPopularTVShows(language).results
                ?.filterNotNull()
                ?.map { tVShowDto -> tVShowDto.toDomain(TV_SHOW) }
                ?: emptyList()
        } catch (e: Exception) {
            Log.e("getPopularTVShows", "Error fetching tv show", e)
            emptyList()
        }
    }
}

object MediaType {
    const val MOVIE = "Movie"
    const val TV_SHOW = "TVShow"
}

fun MovieDto.toDomain(mediaType: String): Media {
    return Media(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        releaseYear = stringToLocalDate(releaseDate ?: ""),
        mediaType = mediaType,
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}"
    )
}

fun TVShowDto.toDomain(mediaType: String): Media {
    return Media(
        id = this.id?.toLong() ?: 0L,
        title = this.name.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        releaseYear = stringToLocalDate(firstAirDate ?: ""),
        mediaType = mediaType,
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}"
    )
}