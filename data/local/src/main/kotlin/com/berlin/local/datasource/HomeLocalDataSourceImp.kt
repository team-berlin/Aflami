package com.berlin.local.datasource

import com.berlin.local.dao.MovieHomeDao
import com.berlin.local.dao.TVShowHomeDao
import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity
import javax.inject.Inject


class HomeLocalDataSourceImp @Inject constructor(
    private val movieHomeDao: MovieHomeDao,
    private val tvShowHomeDao: TVShowHomeDao
) : HomeLocalDataSource {

    override suspend fun getMoviesByType(sectionHome: SectionHome): List<MovieHomeEntity> {
        return movieHomeDao.getMoviesByType(sectionHome)
    }

    override suspend fun addMovies(movies: List<MovieHomeEntity>) {
        movieHomeDao.insertMovies(movies)
    }

    override suspend fun  clearHomeScreenMovies(sectionHome: SectionHome) {
        movieHomeDao.clearHomeScreenMovies(sectionHome)
    }

    override suspend fun getTVShowsByType(sectionHome: SectionHome): List<TVShowHomeEntity> {
        return tvShowHomeDao.getTVShowsByType(sectionHome)
    }

    override suspend fun addTVShows(tvShows: List<TVShowHomeEntity>) {
        tvShowHomeDao.insertTVShows(tvShows)
    }

    override suspend fun clearHomeScreenTVShows(sectionHome: SectionHome) {
        tvShowHomeDao.clearHomeScreenTVShows(sectionHome)
    }
}