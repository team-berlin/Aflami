package com.berlin.local.datasource

import com.berlin.local.dao.MovieHomeDao
import com.berlin.local.dao.TVShowHomeDao
import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.dto.MediaType
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity
import javax.inject.Inject


class HomeLocalDataSourceImp @Inject constructor(
    private val movieHomeDao: MovieHomeDao,
    private val tvShowHomeDao: TVShowHomeDao
) : HomeLocalDataSource {

    override suspend fun getMoviesByType(type: MediaType): List<MovieHomeEntity> {
        return movieHomeDao.getMoviesByType(type)
    }

    override suspend fun addMovies(movies: List<MovieHomeEntity>) {
        movieHomeDao.insertMovies(movies)
    }

    override suspend fun clearMovies(type: MediaType) {
        movieHomeDao.clearMovies(type)
    }

    override suspend fun getTVShowsByType(type: MediaType): List<TVShowHomeEntity> {
        return tvShowHomeDao.getTVShowsByType(type)
    }

    override suspend fun addTVShows(tvShows: List<TVShowHomeEntity>) {
        tvShowHomeDao.insertTVShows(tvShows)
    }

    override suspend fun clearTVShows(type: MediaType) {
        tvShowHomeDao.clearTVShows(type)
    }
}