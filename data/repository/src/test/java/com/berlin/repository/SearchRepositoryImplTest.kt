package com.berlin.repository

import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.util.QueryType
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {

    private val localDataSource = mockk<SearchLocalDataSource>(relaxed = true)
    private val remoteDataSource = mockk<SearchRemoteDataSource>(relaxed = true)
    private lateinit var repository: SearchRepositoryImpl

    @Before
    fun setUp() {
        repository = SearchRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `getMoviesByCountry returns from cache when cache is fresh`() = runTest {
        // Given
        val country = "Eg"
        val language = "en-US"
        val cachedMovies = listOf(fakeCachedMovie(System.currentTimeMillis()))
        coEvery {
            localDataSource.getCachedSearch(
                country,
                QueryType.COUNTRY.name
            )
        } returns cachedMovies

        // When
        val result = repository.getMoviesByCountry(country, language)

        // Then
        coVerify(exactly = 0) { remoteDataSource.searchMoviesByCountry(any(), any()) }
        assertThat(result).isEqualTo(cachedMovies.map { it.toDomain() })
    }

    @Test
    fun `getMoviesByCountry fetches from remote when cache is empty`() = runTest {
        // Given
        val country = "Eg"
        val language = "en-US"
        coEvery {
            localDataSource.getCachedSearch(
                country,
                QueryType.COUNTRY.name
            )
        } returnsMany listOf(
            emptyList(), fakeLocalMovieList()
        )
        coEvery { remoteDataSource.searchMoviesByCountry(any(), any()) } returns fakeMovieDtoList()

        // When
        val result = repository.getMoviesByCountry(country, language)

        // Then
        coVerify(exactly = 1) { remoteDataSource.searchMoviesByCountry(country, language) }
        coVerify(exactly = 1) { localDataSource.cacheSearch(any()) }
        assert(result.isNotEmpty())
    }

    @Test
    fun `searchTVShow returns cached data when not stale`() = runTest {
        val query = "Breaking Bad"
        val language = "en-US"
        val freshTime = System.currentTimeMillis()
        coEvery {
            localDataSource.getCachedSearch(
                query,
                QueryType.TV.name
            )
        } returns fakeCachedTvShows(freshTime)

        val result = repository.searchTVShow(query, language)

        coVerify(exactly = 0) { remoteDataSource.searchTvShows(any(), any()) }
        assertThat(result).hasSize(2)
    }

    @Test
    fun `searchMovie fetches from remote when cache is empty`() = runTest {
        val query = "Inception"
        val language = "en-US"
        coEvery { localDataSource.getCachedSearch(query, QueryType.MOVIE.name) } returnsMany listOf(
            emptyList(), fakeLocalMovieList()
        )
        coEvery { remoteDataSource.searchMovies(any(), any()) } returns fakeMovieList()

        val result = repository.searchMovie(query, language)

        coVerify { remoteDataSource.searchMovies(query, language) }
        coVerify { localDataSource.cacheSearch(any()) }
        assert(result.isNotEmpty())
    }

    @Test
    fun `searchTVShow fetches from remote when cache is stale`() = runTest {
        val query = "Breaking Bad"
        val language = "en-US"
        val staleTime = System.currentTimeMillis() - SearchRepositoryImpl.ONE_HOUR_IN_MILLIS - 1000

        coEvery {
            localDataSource.getCachedSearch(
                query,
                QueryType.TV.name
            )
        } returns fakeCachedTvShows(staleTime)
        coEvery { remoteDataSource.searchTvShows(query, language) } returns fakeTVShowsList()

        val result = repository.searchTVShow(query, language)

        coVerify { remoteDataSource.searchTvShows(query, language) }
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getMoviesByActorName fetches from remote when cache is empty`() = runTest {
        val actor = "Tom Hanks"
        val language = "en-US"

        coEvery { localDataSource.getCachedSearch(actor, QueryType.ACTOR.name) } returnsMany listOf(
            emptyList(), fakeLocalMovieList()
        )

        coEvery {
            remoteDataSource.searchMoviesByActor(actor, language)
        } returns fakeActorDtoList()

        val result = repository.getMoviesByActorName(actor, language)

        coVerify { remoteDataSource.searchMoviesByActor(actor, language) }
        coVerify { localDataSource.cacheSearch(any()) }
        assertThat(result).isNotEmpty()
    }


    private fun fakeCachedMovie(time: Long) = SearchingEntity(
        id = 1,
        query = "Eg",
        title = "Fake Movie",
        rating = 7.2,
        releaseYear = "2024-01-01",
        genre = listOf(1, 2),
        poster = "/fake.jpg",
        type = QueryType.COUNTRY.name,
        time = time
    )

    private fun fakeLocalMovieList(): List<SearchingEntity> {
        return listOf(fakeCachedMovie(System.currentTimeMillis()))
    }

    private fun fakeMovieDtoList(): BaseResponse<MovieDto> {
        return BaseResponse<MovieDto>(
            results = listOf(
                MovieDto(
                    id = 1,
                    title = "Fake Remote Movie",
                    voteAverage = 8.0,
                    releaseDate = "2024-01-01",
                    genreIds = listOf(1, 2),
                    posterPath = "/remote.jpg"
                )
            )
        )
    }

    private fun fakeActorDtoList(): BaseResponse<PersonDto> {
        return BaseResponse<PersonDto>(
            results = listOf(
                PersonDto(
                    id = 101,
                    name = "Tom Hanks",
                    knownForDepartment = "Acting",
                    knownFor = fakeMovieDtoList().results
                ),
                PersonDto(
                    id = 102,
                    name = "Leonardo DiCaprio",
                    knownForDepartment = "Acting",
                    knownFor = fakeMovieDtoList().results
                )
            )
        )
    }

    private fun fakeMovieList(): MovieResponse {
        return MovieResponse(
            results = listOf(
                MovieDto(
                    id = 1,
                    title = "Fake Remote Movie",
                    voteAverage = 8.0,
                    releaseDate = "2024-01-01",
                    genreIds = listOf(1, 2),
                    posterPath = "/remote.jpg"
                )
            )
        )
    }

    private fun fakeTVShowsList(): TVShowResponse {
        return TVShowResponse(
            results = listOf(
                TVShowDto(
                    id = 1,
                    name = "Fake Remote Movie",
                    voteAverage = 8.0,
                    firstAirDate = "2024-01-01",
                    genreIds = listOf(1, 2),
                    posterPath = "/remote.jpg"
                )
            )
        )
    }

    private fun fakeCachedTvShows(time: Long): List<SearchingEntity> {
        return listOf(
            SearchingEntity(
                id = 10,
                title = "Breaking Bad",
                rating = 9.5,
                releaseYear = "2008-01-20",
                genre = listOf(18),
                poster = "/bb.jpg",
                query = "Breaking Bad",
                type = QueryType.TV.name,
                time = time
            ),
            SearchingEntity(
                id = 10,
                title = "Better Call Saul",
                rating = 9.5,
                releaseYear = "2015-01-20",
                genre = listOf(18),
                poster = "/aabb.jpg",
                query = "Better Call Saul",
                type = QueryType.TV.name,
                time = time
            )
        )
    }
}
