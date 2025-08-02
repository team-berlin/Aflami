package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toTVShow
import repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl  @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeRepository {
    override suspend fun getTopRatedMovies(page: Int): List<Movie> =
        homeRemoteDataSource.getTopRatedMovies(page).topRatedMovies.map { movieDto -> movieDto.toDomain() }


    override suspend fun getTopRatedSeries(page: Int): List<TVShow> =
        homeRemoteDataSource.getTopRatedSeries(page).topRatedSeries.map { seriesDto -> seriesDto.toTVShow() }


}