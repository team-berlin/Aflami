package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.MediaType.MOVIE
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toMedia
import com.berlin.repository.mapper.toMovie
import repository.MovieRepository
import kotlin.collections.map

class MovieRepositoryImpl(
    private val localDataSource: ContinueWatchingLocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {

    override suspend fun getContinueWatchingMovies(): List<Movie> {
        return localDataSource.getContinueWatchingMovie().map {
            it.toMovie()
        }
    }

    override suspend fun addContinueWatchingMovie(movie: Movie) {
        localDataSource.addContinueWatchedMovie(movie.toLocalEntity())
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return remoteDataSource.getTopRatedMovies(page).topRatedMovies.map { movieDto -> movieDto.toDomain() }

    }

    override suspend fun getUpComingMovies(): List<Movie> {
        return remoteDataSource.getUpComingMovies().results?.map {
            it!!.toDomain()
        } ?: emptyList()
    }

    override suspend fun getPopularMovies(language: String): List<Movie> {
        return remoteDataSource.getPopularMovies(language).results?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain(MOVIE) } ?: emptyList()
    }

    override suspend fun getMoviesByCountry(
        query: String,
        page: Int
    ): List<Movie> {
        val movies = localDataSource.getCachedSearch(query, QueryType.COUNTRY, page = page)
        if (!isExpiredOrEmpty(movies)) return movies.map { it.toDomain() }

        remoteDataSource.searchMoviesByCountry(query, language, page).results?.filterNotNull()
            ?.map { it.toLocal(query, QueryType.COUNTRY.name, page, "Movie") }
            ?.also { localDataSource.cacheSearch(it) }

        return localDataSource.getCachedSearch(query, QueryType.COUNTRY, page = page)
            .map { it.toDomain() }
    }

    override suspend fun getMediaByActorName(actorName: String, page: Int): List<Movie> {
        val cached = localDataSource.getCachedSearch(
            actorName, QueryType.ACTOR, pageSize = 20, page = page
        )

        val mediaList = if (!isExpiredOrEmpty(cached)) {
            cached.map { it.toMedia() }
        } else {
            remoteDataSource.searchMoviesByActor(actorName, language, page).results?.filterNotNull()
                ?.also { getActingDepartment(it) }?.let { getMediaByActorName(actorName, page, it) }
                ?.also { localDataSource.cacheSearch(it) }?.map { it.toMedia() } ?: emptyList()
        }

        return sortMediaByCategoryScore(mediaList)
    }

    override suspend fun searchMovie(
        query: String,
        page: Int
    ): List<Movie> {
        return (localDataSource.getCachedSearch(query, QueryType.MOVIE, pageSize = 20, page = page)
            .takeIf { !isExpiredOrEmpty(it) }?.map { it.toDomain() }
            ?: remoteDataSource.searchMovies(query, language, page).results?.filterNotNull()?.map {
                it.toLocal(
                    query, QueryType.MOVIE.name, page, "Movie"
                )
            }?.also { localDataSource.cacheSearch(it) }?.map { it.toDomain() } ?: emptyList())
    }

    override suspend fun getRecentMoviesSearchQueries(): List<String> {
        return localDataSource.getRecentSearchQueries()
    }

    override suspend fun saveRecentMoviesHistory(query: String) {
        val entity = SearchingEntity(
            id = query.hashCode().toLong(),
            query = query,
            type = QueryType.HISTORY.name,
            time = System.currentTimeMillis(),
            title = "",
            rating = 0.0,
            releaseYear = "",
            genre = emptyList(),
            poster = "",
            page = 1,
            mediaType = ""

        )
        localDataSource.insertQueryOnly(entity)
    }

    override suspend fun deleteMovieQueryFromHistory(query: String) {
        localDataSource.deleteQueryFromHistory(query)
    }

    override suspend fun clearMovieSearchHistory() {
        localDataSource.clearSearchHistory()
    }

}

object MediaType {
    const val MOVIE = "Movie"
    const val TV_SHOW = "TVShow"
}