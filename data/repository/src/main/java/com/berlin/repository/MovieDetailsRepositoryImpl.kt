package com.berlin.repository

import android.util.Log
import com.berlin.entity.Genre
import com.berlin.entity.MediaCast
import com.berlin.entity.MediaImage
import com.berlin.entity.Movie
import com.berlin.entity.MovieDetails
import com.berlin.entity.Video
import com.berlin.entity.Review
import com.berlin.repository.datasource.local.GenreLocalDataSource
import com.berlin.repository.datasource.local.dto.GenreEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toGenreEntity
import com.berlin.repository.util.Constants
import com.berlin.repository.util.Constants.GENRE_TYPE_MOVIE
import exceptions.AflamiExceptions
import repository.MovieDetailsRepository
import java.time.Instant

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val genreLocalDataSource: GenreLocalDataSource
) : MovieDetailsRepository {


    override suspend fun getMovieCastDetails(movieId: Long): List<MediaCast> {
        return remoteDataSource.getMovieCastDetails(
            movieId
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        } ?: emptyList()
    }

    override suspend fun getMovieImages(movieId: Long): MediaImage {
        return try {
            val imagesResponse = remoteDataSource.getMovieImages(movieId)

            Log.d("Repository", "Backdrops: ${imagesResponse.backdrops}")
            Log.d("Repository", "Posters: ${imagesResponse.posters}")

            val backdrops = imagesResponse.backdrops
                ?.mapNotNull { it.filePath?.let { path -> POSTER_PREFIX + path } }

            val posters = imagesResponse.posters
                ?.mapNotNull { it.filePath?.let { path -> POSTER_PREFIX + path } }

            MediaImage(backdrops = backdrops.orEmpty(), posters = posters.orEmpty())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieDetails(id: Long): MovieDetails? {
        return try {
            remoteDataSource.getMovieDetails(id).toDomain()
        } catch (exception: AflamiExceptions) {
            throw exception
        }
    }

    override suspend fun getMovieSimilar(movieId: Long): List<Movie> {
        return remoteDataSource.getMovieSimilar(movieId).results?.mapNotNull { movieDto ->
            movieDto?.toDomain()
        } ?: emptyList()
    }

    override suspend fun getReviews(id: Long): List<Review> {
        return remoteDataSource.getMovieReviews(id).results?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() } ?: emptyList()
    }

    override suspend fun getMovieGenres(): List<Genre> {
        val cachedGenres = genreLocalDataSource.getCachedGenres(GENRE_TYPE_MOVIE)
        if (!isExpiredOrEmpty(cachedGenres)) {
            return cachedGenres.map { it.toDomain() }
        }

        val genres = remoteDataSource.getMovieGenres().genres.map { it.toGenreEntity(GENRE_TYPE_MOVIE) }
        genreLocalDataSource.cacheGenres(genres)
        return genres.map { it.toDomain() }
    }

    override suspend fun getMovieVideos(id: Long): List<Video> {
        return remoteDataSource.getMovieVideos(id).results?.mapNotNull {
            it?.toDomain()
        } ?: emptyList()

    }

    private fun isExpiredOrEmpty(list: List<GenreEntity>): Boolean {
        return list.isEmpty() || list.any { Instant.now().toEpochMilli() - it.time >Constants.CACHE_TIMEOUT }
    }
}