package com.berlin.repository

import android.util.Log
import com.berlin.entity.ContinueWatchingMoviesModel
import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toRecentMovieEntity
import repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource,
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource,
    private val remoteDataSource: RemoteDataSource,

    ) : MovieRepository {

    override suspend fun getContinueWatchingMovies(page: Int): List<Movie> {
        return recentlyWatchedLocalDataSource.getRecentlyWatchedMovie(page = page).map {
            it.toDomain()
        }
    }

    override suspend fun addContinueWatchingMovie(movie: Movie) {
        recentlyWatchedLocalDataSource.addRecentlyWatchedMovie(movie.toRecentMovieEntity())
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return remoteDataSource.getTopRatedMovies(page).results?.mapNotNull { it.toDomain() }
            ?: emptyList()

    }

    override suspend fun getUpComingMovies(): List<Movie> {
        return remoteDataSource.getUpComingMovies().results?.map {
            it.toDomain()
        } ?: emptyList()
    }


    override suspend fun getPopularMovies(): List<Movie> {
        return remoteDataSource.getPopularMovies().results
            ?.map { movieDto -> movieDto.toDomain() } ?: emptyList()
    }

    override suspend fun getMoviesByMoods(moods: List<Int>): List<Movie> {
            if (moods.isEmpty()) return emptyList()
            return remoteDataSource.getMoviesByMoodIds(moods).results?.mapNotNull {
                it.toDomain()
            } ?: emptyList()
        }



    override suspend fun getMoviesByCountry(
        query: String,
        page: Int,
    ): List<Movie> {
        return remoteDataSource
            .getMoviesByCountryName(query, page).results
            ?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getMoviesByActorName(actorName: String, page: Int): List<Movie> {
        return remoteDataSource.getMoviesByCountryName(
            actorName,
            page
        ).results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getMovieByKeyWord(
        query: String,
        page: Int,
    ): List<Movie> {
        return remoteDataSource.getMoviesByKeyword(query, page).results?.map { it.toDomain() }
            ?: emptyList()

    }

    override suspend fun getRecentMoviesSearchQueries(): List<String> {
        return recentHistoryLocalDataSource.getRecentSearchQueries()
    }

    override suspend fun saveRecentMoviesHistory(query: String) {
        val entity = SearchingEntity(
            query = query,
            type = QueryType.HISTORY.name,
            queryType = QueryType.MOVIE,
        )
        recentHistoryLocalDataSource.insertQueryOnly(entity)
    }

    override suspend fun deleteMovieQueryFromHistory(query: String) {
        recentHistoryLocalDataSource.deleteQueryFromHistory(query)
    }

    override suspend fun clearMovieSearchHistory() {
        recentHistoryLocalDataSource.clearSearchHistory()
    }

}

