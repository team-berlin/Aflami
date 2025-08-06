package com.berlin.local.datasource

import com.berlin.local.dao.HomeMovieDao
import com.berlin.local.dao.HomeTVShowDao
import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity
import javax.inject.Inject


class HomeLocalDataSourceImp @Inject constructor(
    private val homeMovieDao: HomeMovieDao,
    private val homeTVShowDao: HomeTVShowDao
) : HomeLocalDataSource {

    override suspend fun getMoviesBySection(sectionHome: SectionHome): List<MovieHomeEntity> {
        return homeMovieDao.getMoviesBySection(sectionHome)
    }

    override suspend fun addMovies(movies: List<MovieHomeEntity>) {
        homeMovieDao.insertMovies(movies)
    }

    override suspend fun  clearHomeScreenMovies(sectionHome: SectionHome) {
        homeMovieDao.clearHomeScreenMovies(sectionHome)
    }

    override suspend fun getTVShowsBySection(sectionHome: SectionHome): List<TVShowHomeEntity> {
        return homeTVShowDao.getTVShowsBySection(sectionHome)
    }

    override suspend fun addTVShows(tvShows: List<TVShowHomeEntity>) {
        homeTVShowDao.insertTVShows(tvShows)
    }

    override suspend fun clearHomeScreenTVShows(sectionHome: SectionHome) {
        homeTVShowDao.clearHomeScreenTVShows(sectionHome)
    }
}