package com.berlin.repository

import com.berlin.entity.Actor
import com.berlin.entity.Genre
import com.berlin.entity.MediaImage
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.entity.Video
import com.berlin.exception.AflamiException
import com.berlin.repository.datasource.local.GenreLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.MoviesGenreEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toMoviesGenreEntity
import com.berlin.repository.util.Constants
import com.berlin.repository.util.MediaUrls
import com.berlin.repository.util.tmdbImageUrl
import repository.MovieDetailsRepository
import java.time.Instant
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource,
    private val genreLocalDataSource: GenreLocalDataSource
) : MovieDetailsRepository {

    override suspend fun getMovieImages(movieId: Long): MediaImage {
        return try {
            val imagesResponse = remoteDataSource.getMovieImages(movieId)

            val backdrops = imagesResponse.backdrops
                ?.mapNotNull {
                    it.filePath?.let { path -> tmdbImageUrl(path = path, size = MediaUrls.TmdbImageSize.W500) }
                }

            val posters = imagesResponse.posters
                ?.mapNotNull { it.filePath?.let { path -> tmdbImageUrl(path = path,MediaUrls.TmdbImageSize.W500) }
                }
            recentlyWatchedLocalDataSource
            MediaImage(backdrops = backdrops.orEmpty(), posters = posters.orEmpty())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieDetails(id: Long): Movie {
        val review =
            remoteDataSource.getMovieReviews(id).results?.map { it.toDomain() }.orEmpty()
        return try {
            remoteDataSource.getMovieDetails(id).toDomain(review)
        } catch (exception: AflamiException) {
            throw exception
        }
    }

    override suspend fun getMovieActors(movieId: Long): List<Actor> {
        return remoteDataSource.getMovieCastDetails(
            movieId
        ).cast?.mapNotNull { castItemDto ->
            castItemDto.toDomain()
        } .orEmpty()
    }

    override suspend fun getSimilarMovies(movieId: Long): List<Movie> {
        val review =
            remoteDataSource.getMovieReviews(movieId).results?.map { it.toDomain() } .orEmpty()
        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference()
            .associate { it.categoryId to it.count }
        return remoteDataSource.getSimilarMovies(movieId).results?.mapNotNull { movieDto ->
            movieDto.toDomain(review)
        }?.sortedByDescending { movie ->
            movie.genres.sumOf { genre ->
                genreScoresMap[genre.id] ?: 0
            }
        }.orEmpty()
    }

    override suspend fun getMovieReviews(movieId: Long): List<Review> {
        return remoteDataSource.getMovieReviews(movieId).results?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() }.orEmpty()
    }


    override suspend fun getMovieGenres(): List<Genre> {
        val cachedGenres = genreLocalDataSource.getCachedMovieGenres()
        if (!isExpiredOrEmpty(cachedGenres)) {
            return cachedGenres.map { it.toDomain() }
        }

        val remoteGenres = remoteDataSource.getMovieGenres().genres
        val genreEntities = remoteGenres.map { it.toMoviesGenreEntity() }
        genreLocalDataSource.cacheMovieGenres(genreEntities)
        return remoteGenres.map { it.toDomain() }
    }

    override suspend fun getMovieVideos(id: Long): List<Video> {
        return remoteDataSource.getMovieVideos(id).results?.mapNotNull {
            it?.toDomain()
        }.orEmpty()

    }

    private fun isExpiredOrEmpty(list: List<MoviesGenreEntity>): Boolean {
        return list.isEmpty() || list.any {
            Instant.now().toEpochMilli() - it.time > Constants.CACHE_TIMEOUT
        }
    }
}