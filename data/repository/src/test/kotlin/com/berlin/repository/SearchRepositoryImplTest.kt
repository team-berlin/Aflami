package com.berlin.repository

import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.mapper.toDomain
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchRepositoryImplTest {

    private lateinit var remoteDataSource: SearchRemoteDataSource
    private lateinit var repository: SearchRepositoryImpl

    @BeforeEach
    fun setup() {
        remoteDataSource = mockk(relaxed = true)
        repository = SearchRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `searchByCountry should return mapped movies when results are valid`() = runTest {
        // Given
        val country = "EG"
        val movieDto = dummyMovieDto
        val movie = movieDto.toDomain()

        val response = MovieResponse(results = listOf(movieDto, movieDto, movieDto))
        coEvery { remoteDataSource.searchMoviesByCountry(country) } returns response

        // When
        val result = repository.searchByCountry(country)

        // Then
        assertThat(result).containsExactly(movie, movie, movie)
    }

    @Test
    fun `searchByCountry should return empty list when results is null`() = runTest {
        // Given
        val country = "EG"
        val response = MovieResponse(1, 10, results = null, 2)
        coEvery { remoteDataSource.searchMoviesByCountry(country) } returns response

        // When
        val result = repository.searchByCountry(country)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `searchByCountry should filter out null entries`() = runTest {
        // Given
        val country = "EG"
        val movieDto = dummyMovieDto
        val movie = movieDto.toDomain()

        val response = MovieResponse(results = listOf(movieDto, null, movieDto))
        coEvery { remoteDataSource.searchMoviesByCountry(country) } returns response

        // When
        val result = repository.searchByCountry(country)

        // Then
        assertThat(result).containsExactly(movie, movie)
    }

    private val dummyMovieDto = MovieDto(
        id = 101,
        overview = "An epic movie about courage and friendship.",
        originalLanguage = "en",
        originalTitle = "Epic Original Title",
        video = false,
        title = "Epic Movie",
        genreIds = listOf(12, 18),
        posterPath = "/poster/path.jpg",
        backdropPath = "/backdrop/path.jpg",
        releaseDate = "2023-05-15",
        popularity = 123.45,
        voteAverage = 8.7,
        adult = false,
        voteCount = 2345
    )

}