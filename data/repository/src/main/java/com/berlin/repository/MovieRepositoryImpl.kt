package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toMovieByMoodEntity
import com.berlin.repository.mapper.toPopularMovieEntity
import com.berlin.repository.mapper.toRecentMovieEntity
import com.berlin.repository.mapper.toTopRateMovieEntity
import com.berlin.repository.mapper.toUpComingMovieEntity
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
        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference()
            .associate { it.categoryId to it.count }

        return recentlyWatchedLocalDataSource.getRecentlyWatchedMovie(page = page).map {
            it.toDomain()
        }.sortedByDescending { movie ->
            movie.genres.sumOf { genre ->
                genreScoresMap[genre.id] ?: 0
            }
        }
    }

    override suspend fun addContinueWatchingMovie(movie: Movie) {
        recentlyWatchedLocalDataSource.addRecentlyWatchedMovie(movie.toRecentMovieEntity())
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        val remoteMovies =
            remoteDataSource.getTopRatedMovies(page).results?.map { it.toDomain() }.orEmpty()
        if (remoteMovies.isNotEmpty()) {
            homeLocalDataSource.addMovies(remoteMovies.map { it.toTopRateMovieEntity() })
        }
        val movies = homeLocalDataSource.getMoviesBySection(SectionHome.TOP_RATING)
        return movies.map { it.toDomain() }
    }

    override suspend fun getUpComingMovies(genreId: Long): List<Movie> {
        val remoteMovies =
            remoteDataSource.getUpComingMovies(genreId).results?.map { it.toDomain() }.orEmpty()
        if (remoteMovies.isNotEmpty()) {
            homeLocalDataSource.addMovies(remoteMovies.map { it.toUpComingMovieEntity(genreId) })
        }
        val movies = homeLocalDataSource.getUpcomingMoviesByGenre(SectionHome.UPCOMING,genreId)
        return movies.map { it.toDomain() }
    }


    override suspend fun getPopularMovies(): List<Movie> {
        val remoteMovies =
            remoteDataSource.getPopularMovies().results?.map { it.toDomain() }.orEmpty()
        if (remoteMovies.isNotEmpty()) {
            homeLocalDataSource.addMovies(remoteMovies.map { it.toPopularMovieEntity() })
        }
        val movies = homeLocalDataSource.getMoviesBySection(SectionHome.POPULAR)
        return movies.map { it.toDomain() }
    }

    override suspend fun getMoviesByMoods(moods: List<Int>): List<Movie> {
        val remoteMovies =
            remoteDataSource.getMoviesByMoodIds(moods).results?.map { it.toDomain() }.orEmpty()
        if (remoteMovies.isNotEmpty()) {
            homeLocalDataSource.addMovies(remoteMovies.map { it.toMovieByMoodEntity() })
        }
        val movies = homeLocalDataSource.getMoviesBySection(SectionHome.BY_MOOD)
        return movies.map { it.toDomain() }
    }


    override suspend fun getMoviesByCountry(
        query: String,
        page: Int,
    ): List<Movie> {
        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference()
            .associate { it.categoryId to it.count }
        return remoteDataSource.getMoviesByCountryName(query, page).results?.map { it.toDomain() }
            ?.sortedByDescending { movie ->
                movie.genres.sumOf { genre ->
                    genreScoresMap[genre.id] ?: 0
                }
            }.orEmpty()

    }

    override suspend fun getMoviesByActorName(actorName: String, page: Int): List<Movie> {

        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference()
            .associate { it.categoryId to it.count }
        return remoteDataSource.getMoviesByActorName(
            actorName,
            page
        ).results?.filter { it.knownForDepartment == ACTING_DEPARTMENT }?.flatMap { personDto ->
            personDto.knownFor?.filter { it.mediaType == MOVIE_MEDIA_TYPE } .orEmpty()
        }?.map { it.toDomain() }?.sortedByDescending { movie ->
            movie.genres.sumOf { genre ->
                genreScoresMap[genre.id] ?: 0
            }
        }.orEmpty()
    }

    override suspend fun getMovieByKeyWord(
        query: String,
        page: Int,
    ): List<Movie> {
        return remoteDataSource.getMoviesByKeyword(query, page).results?.map { it.toDomain() }
            .orEmpty()

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

    override suspend fun getMoviesByCategory(
        genreId: Long, page: Int
    ): List<Movie> {
        return remoteDataSource.getMoviesByCategory(genreId, page).results?.map { it.toDomain() }
            .orEmpty()
    }

    override suspend fun getMovieGame(): List<Movie> {
        return remoteDataSource.getMovieGame().results?.map {
            it.toDomain()
        }.orEmpty()
    }

}




