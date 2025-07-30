package com.berlin.repository.fake

import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.TopRatedMoviesResponse
import com.berlin.repository.datasource.remote.dto.TopRatedSeriesResponse

class FakeHomeRemoteDataSource : HomeRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse {
        return TopRatedMoviesResponse(
            listOf(
                MovieDto(
                    id = 11,
                    title = "Movie",
                    genreIds = listOf(),
                    posterPath = "hghg",
                    releaseDate = "gfdg",
                    popularity = 3.4,
                    voteAverage = 3.4,
                )
            ), page = 2854
        )
    }

    override suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse {
        return TopRatedSeriesResponse(
            topRatedSeries = listOf(
                TVShowDto(
                    id = 1,
                    name = "fgfd",
                    genreIds = listOf(),
                    posterPath = "ghfgf",
                    firstAirDate = "dgf",
                    voteAverage = 4.3,
                    EpisodeDto = 1
                )
            ), page = 1
        )
    }
}