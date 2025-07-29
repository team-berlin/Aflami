package com.berlin.repository

import com.berlin.entity.Media
import com.berlin.entity.Movie
import com.berlin.repository.MediaType.MOVIE
import com.berlin.repository.MediaType.TVSHOW
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {

    override suspend fun getUpComingMovies(): List<Movie> {
        return remoteDataSource.getUpComingMovies().results?.map {
            it!!.toDomain()
        }?: emptyList()
    }

    override suspend fun getPopularMovies(language: String): List<Media> {
        return remoteDataSource.getPopularMovies(language).results?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain(MOVIE) } ?: emptyList()
    }

    override suspend fun getPopularTVShows(language: String): List<Media> {
        return remoteDataSource.getPopularTVShows(language).results?.filterNotNull()
            ?.map { tVShowDto -> tVShowDto.toDomain(TVSHOW) } ?: emptyList()
    }

    override suspend fun getMoviesByMoods(
        moods: List<Int>
    ): List<Movie> {
        if (moods.isEmpty()) return emptyList()
        return remoteDataSource.getMoviesByMoodIds(moods).results?.mapNotNull {
            it?.toDomain()
        } ?: emptyList()
    }
}

object MediaType {
    const val MOVIE = "MOVIE"
    const val TVSHOW = "TVSHOW"
}