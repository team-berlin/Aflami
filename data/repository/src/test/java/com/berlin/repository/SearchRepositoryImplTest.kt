package com.berlin.repository

import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.mapper.toLocal
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchRepositoryImplTest {
    private lateinit var remoteDataSource: SearchRemoteDataSource
    private lateinit var localDataSource: SearchLocalDataSource

    private lateinit var repository: SearchRepositoryImpl

    @BeforeEach
    fun setup() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk(relaxed = true)
        repository = SearchRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `searchByCountry should return mapped movies when results are valid`() = runTest {
        // Given
        val country = "EG"
        val language = "en-US"
        val movieDto = dummyMovieDto
        val movie = movieDto.toLocal(query = country, time = System.currentTimeMillis())

        val response = BaseResponse(results = listOf(movieDto, movieDto, movieDto))
        coEvery { remoteDataSource.searchMoviesByCountry(country, language) } returns response

        // When
        val result = repository.getMoviesByCountry(country, language)

        // Then
        assertThat(result).containsExactly(movie, movie, movie)
    }

    @Test
    fun `searchByCountry should return empty list when results is null`() = runTest {
        // Given
        val country = "EG"
        val language = "en-US"
        val response = BaseResponse<MovieDto>(1, 10, results = null, 2)
        coEvery { remoteDataSource.searchMoviesByCountry(country, language) } returns response

        // When
        val result = repository.getMoviesByCountry(country, language)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `searchByCountry should filter out null entries`() = runTest {
        // Given
        val country = "EG"
        val language = "en-US"
        val movieDto = dummyMovieDto
        val movie = movieDto.toLocal(query = country, time = System.currentTimeMillis())

        val response = BaseResponse(results = listOf(movieDto, null, movieDto))
        coEvery { remoteDataSource.searchMoviesByCountry(country, language) } returns response

        // When
        val result = repository.getMoviesByCountry(country, language)

        // Then
        assertThat(result).containsExactly(movie, movie)
    }

    private val dummyMovieDto = MovieDto(
        id = 101,
        title = "Epic Movie",
        genreIds = listOf(12, 18),
        posterPath = "/poster/path.jpg",
        releaseDate = "2023-05-15",
        popularity = 123.45,
        voteAverage = 8.7,

    )
}