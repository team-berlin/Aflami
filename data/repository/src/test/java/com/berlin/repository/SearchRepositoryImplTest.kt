package com.berlin.repository

import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.SearchRepository
import java.time.Instant

class SearchRepositoryImplTest {

    private lateinit var remoteDataSource: SearchRemoteDataSource
    private lateinit var localDataSource: SearchLocalDataSource
    private lateinit var repository: SearchRepository

    @Before
    fun setup() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk(relaxed = true)
        repository = SearchRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `should call getCachedSearch when getMoviesByCountry is called`() = runTest {
        // Given
        coEvery {
            localDataSource.getCachedSearch(
                "EG",
                QueryType.COUNTRY,
                20,
                1
            )
        } returns cachedMovies

        // When
        repository.getMoviesByCountry("EG", 1)

        // Then
        coVerify { localDataSource.getCachedSearch("EG", QueryType.COUNTRY, 20, 1) }
    }

    @Test
    fun `should call searchMoviesByCountry when cache is empty`() = runTest {
        // Given
        coEvery {
            localDataSource.getCachedSearch(
                "EG",
                QueryType.COUNTRY,
                20,
                1
            )
        } returns emptyList()

        // When
        repository.getMoviesByCountry("EG", 1)

        // Then
        coVerify { remoteDataSource.searchMoviesByCountry("EG", "en-US", 1) }
    }

    @Test
    fun `searchByCountry should return cached movies when cache is not empty`() = runTest {
        // Given
        coEvery {
            localDataSource.getCachedSearch(
                "EG",
                QueryType.COUNTRY,
                20,
                1
            )
        } returns cachedMovies

        // When
        val result = repository.getMoviesByCountry("EG", 1)

        // Then
        assertThat(result).containsExactlyElementsIn(cachedMovies.map { it.toDomain() })
    }

    @Test
    fun `searchByCountry should call remoteDataSource cached is not empty but expired`() = runTest {
        // Given
        coEvery {
            localDataSource.getCachedSearch(
                "EG",
                QueryType.COUNTRY,
                20,
                1
            )
        } returns expiredCached

        // When
        repository.getMoviesByCountry("EG", 1)

        // Then
        coVerify { remoteDataSource.searchMoviesByCountry("EG", "en-US", 1) }
    }

    @Test
    fun `should not call cacheMovies when results is null`() = runTest {
        // Given
        val response = BaseResponse<MovieDto>(results = null)
        coEvery {
            localDataSource.getCachedSearch(
                "EG",
                QueryType.COUNTRY,
                20,
                1
            )
        } returns emptyList()
        coEvery { remoteDataSource.searchMoviesByCountry("EG", "en-US", 1) } returns response

        // When
        repository.getMoviesByCountry("EG", 1)

        // Then
        coVerify(exactly = 0) { localDataSource.cacheSearch(any()) }
    }

    private val dummyMovieDtos = (0..5).map {
        MovieDto(
            id = it,
            title = "Epic Movie",
            genreIds = listOf(12, 18),
            posterPath = "/poster/path.jpg",
            releaseDate = "2023-05-15",
            popularity = 123.45,
            voteAverage = 8.7
        )
    }

    private val cachedMovies =
        dummyMovieDtos.map { it.toLocal(query = "EG", type = QueryType.COUNTRY) }

    private val expiredCached = (0..3).map {
        SearchingEntity(
            id = it.toLong(),
            query = "EG",
            type = QueryType.COUNTRY,
            time = Instant.now().epochSecond - CACHE_TIMEOUT,
            title = "Epic Movie",
            genre = listOf(12, 18),
            poster = "/poster/path.jpg",
            releaseYear = "2023",
            rating = 8.7
        )
    }

    companion object {
        const val CACHE_TIMEOUT = 10_000_000L
    }
}