package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.datasource.remote.dto.TopRatedResponse
import repository.HomeRepository

class HomeRepositoryImpl(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeRepository {
    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        homeRemoteDataSource.getTopRatedMovies(page)
    }

    override suspend fun getTopRatedSeries(page: Int): List<TVShow> {
        homeRemoteDataSource.getTopRatedSeries(page)
    }

}