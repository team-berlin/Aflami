package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toPopularMovieEntity
import com.berlin.repository.mapper.toRecentMovieEntity
import com.berlin.repository.mapper.toTopRateMovieEntity
import com.berlin.repository.mapper.toUpComingMovieEntity
import com.berlin.repository.util.Constants
import com.berlin.repository.util.Constants.ACTING_DEPARTMENT
import com.berlin.repository.util.Constants.MOVIE_MEDIA_TYPE
import repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource,
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource,
    private val homeLocalDataSource: HomeLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : MovieRepository {

    override suspend fun getContinueWatchingMovies(page: Int): List<Movie> {
        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference().associate { it.categoryId to it.count }

        return recentlyWatchedLocalDataSource.getRecentlyWatchedMovie(page = page).map {
            it.toDomain()
        }.sortedByDescending { movie-> movie.genres.sumOf { genre-> genreScoresMap[genre.id]?:0 } }

    }

    override suspend fun addContinueWatchingMovie(movie: Movie) {
        recentlyWatchedLocalDataSource.addRecentlyWatchedMovie(movie.toRecentMovieEntity())
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        val localMovies = homeLocalDataSource.getMoviesBySection(SectionHome.TOP_RATING)
        if (!isExpiredOrEmpty(localMovies)&&localMovies.isNotEmpty()) {
            return localMovies.map { it.toDomain() }
        }

        val remoteMovies =
            remoteDataSource.getTopRatedMovies(page).results?.mapNotNull { it.toDomain() }
                ?: emptyList()
        if (remoteMovies.isNotEmpty()) {
            homeLocalDataSource.clearHomeScreenMovies(SectionHome.TOP_RATING)
            homeLocalDataSource.addMovies(remoteMovies.map { it.toTopRateMovieEntity() })
        }

        return remoteMovies
    }

    override suspend fun getUpComingMovies(): List<Movie> {
        val localMovies = homeLocalDataSource.getMoviesBySection(SectionHome.UPCOMING)
        if (!isExpiredOrEmpty(localMovies) &&localMovies.isNotEmpty()) {
            return localMovies.map { it.toDomain() }
        }

        val remoteMovies = remoteDataSource.getUpComingMovies()
            .results?.map { it.toDomain() } ?: emptyList()
        if (remoteMovies.isNotEmpty()) {
            homeLocalDataSource.clearHomeScreenMovies(SectionHome.UPCOMING)
            homeLocalDataSource.addMovies(remoteMovies.map { it.toUpComingMovieEntity() })
        }

        return remoteMovies
    }


    override suspend fun getPopularMovies(): List<Movie> {
        val localMovies = homeLocalDataSource.getMoviesBySection(SectionHome.POPULAR)
        if (!isExpiredOrEmpty(localMovies) && localMovies.isNotEmpty()) {
            return localMovies.map { it.toDomain() }
        }

        val remoteMovies = remoteDataSource.getPopularMovies().results?.map { it.toDomain() }
            ?: emptyList()
        if (remoteMovies.isNotEmpty()) {
            homeLocalDataSource.clearHomeScreenMovies(SectionHome.POPULAR)
            homeLocalDataSource.addMovies(remoteMovies.map { it.toPopularMovieEntity() })
        }

        return remoteMovies
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
        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference().associate { it.categoryId to it.count }
        return remoteDataSource
            .getMoviesByCountryName(query, page).results
            ?.map { it.toDomain() }
            ?.sortedByDescending { movie-> movie.genres.sumOf { genre-> genreScoresMap[genre.id]?:0 } }

            ?: emptyList()
    }

    override suspend fun getMoviesByActorName(actorName: String, page: Int): List<Movie> {

        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference().associate { it.categoryId to it.count }
        return remoteDataSource.getMoviesByActorName(actorName, page)
            .results
            ?.filter { it.knownForDepartment == ACTING_DEPARTMENT }
            ?.flatMap { personDto ->
                personDto.knownFor?.filter { it.mediaType == MOVIE_MEDIA_TYPE } ?: emptyList()
            }?.map { it.toDomain() }
            ?.sortedByDescending { movie-> movie.genres.sumOf { genre-> genreScoresMap[genre.id]?:0 } }
            ?: emptyList()
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

    override suspend fun getMovieGame(): List<Movie> {
        return remoteDataSource.getMovieGame().results?.map {
            it.toDomain()
        }?: emptyList()
    }

    private fun isExpiredOrEmpty(list: List<MovieHomeEntity>): Boolean {
        return list.isEmpty() || list.any {
            System.currentTimeMillis() - it.addedAt > Constants.HOME_CACHE_TIMEOUT_MILLIS
        }
    }
}



