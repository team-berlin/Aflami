package com.berlin.repository.impl

import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.google.common.truth.Truth
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class SearchRepositoryImplTest {

    private lateinit var remoteDataSource: SearchRemoteDataSource
    private lateinit var localDataSource: SearchLocalDataSource

    private lateinit var repository: SearchRepositoryImpl

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
    fun `searchByCountry should return mapped movies when results are valid`() = runTest {
        // Given
        val country = "EG"
        val language = "en-US"
        val movieDto = dummyMovieDto

        val movie1 = movieDto.toLocal(query = country, time = 123L, type = "COUNTRY")
        val mapped = movie1.toDomain()

        val response = BaseResponse(results = listOf(movieDto, movieDto, movieDto))

       
        coEvery { localDataSource.getCachedSearch(country, "COUNTRY") } returnsMany listOf(
            emptyList(), // First call triggers remote fetch
            listOf(movie1, movie1, movie1) // After caching
        )

        coEvery { remoteDataSource.searchMoviesByCountry(country, language) } returns response
        coJustRun { localDataSource.cacheSearch(any()) }

        // When
        val result = repository.getMoviesByCountry(country, language)

        // Then
        println(result)
        Truth.assertThat(result).containsExactly(mapped, mapped, mapped)
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
        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun `getMoviesByActorName returns mapped movies when cache is empty and remote data is valid`() =
        runTest {
            val actorName = "Tom Hanks"
            val language = "en-US"
            val dto = dummyPersonDto
            val local = dto.knownFor!!.first()!!.toLocal(actorName, 123L, "ACTOR")
            val mapped = local.toDomain()

            val response = BaseResponse(results = listOf(dto))

            coEvery { localDataSource.getCachedSearch(actorName, "ACTOR") } returnsMany listOf(
                emptyList(),
                listOf(local, local)
            )
            coEvery { remoteDataSource.searchMoviesByActor(actorName, language) } returns response
            coJustRun { localDataSource.cacheSearch(any()) }

            val result = repository.getMoviesByActorName(actorName, language)

            Truth.assertThat(result).containsExactly(mapped, mapped)
        }

    @Test
    fun `getMoviesByActorName returns cached data when cache is fresh`() = runTest {
        val actorName = "Tom Hanks"
        val language = "en-US"
        val movie = dummyMovieDto.toLocal(actorName, System.currentTimeMillis(), "ACTOR")
        val mapped = movie.toDomain()

        coEvery { localDataSource.getCachedSearch(actorName, "ACTOR") } returns listOf(movie)

        val result = repository.getMoviesByActorName(actorName, language)

        Truth.assertThat(result).containsExactly(mapped)
    }


    @Test
    fun `getMoviesByActorName returns empty list when remote result is null`() = runTest {
        val actorName = "Tom Hanks"
        val language = "en-US"
        val response = BaseResponse<PersonDto>(results = null)

        coEvery { localDataSource.getCachedSearch(actorName, "ACTOR") } returns emptyList()
        coEvery { remoteDataSource.searchMoviesByActor(actorName, language) } returns response
        coJustRun { localDataSource.cacheSearch(any()) }

        val result = repository.getMoviesByActorName(actorName, language)

        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun `getMoviesByActorName skips people not in Acting department`() = runTest {
        val actorName = "Someone"
        val language = "en-US"
        val nonActor = dummyPersonDto.copy(knownForDepartment = "Directing")

        val response = BaseResponse(results = listOf(nonActor))

        coEvery { localDataSource.getCachedSearch(actorName, "ACTOR") } returns emptyList()
        coEvery { remoteDataSource.searchMoviesByActor(actorName, language) } returns response
        coJustRun { localDataSource.cacheSearch(any()) }

        val result = repository.getMoviesByActorName(actorName, language)

        Truth.assertThat(result).isEmpty()
    }
    
    @Test
    fun `getRecentSearchQueries should return list from localDataSource`() = runTest {
        val expected = listOf("Avengers", "Dark Knight")
        coEvery { localDataSource.getRecentSearchQueries() } returns expected

        val result = repository.getRecentSearchQueries()

        Truth.assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { localDataSource.getRecentSearchQueries() }
    }

    @Test
    fun `saveRecentHistory should insert query as SearchingEntity`() = runTest {
        val query = "Spider Man"
        coEvery { localDataSource.insertQueryOnly(any()) } just Runs

        repository.saveRecentHistory(query)

        coVerify(exactly = 1) {
            localDataSource.insertQueryOnly(withArg {
                Truth.assertThat(it.id).isEqualTo(query.hashCode().toLong())
                Truth.assertThat(it.query).isEqualTo(query)
                Truth.assertThat(it.type).isEqualTo("HISTORY")
                Truth.assertThat(it.title).isEqualTo("")
                Truth.assertThat(it.rating).isEqualTo(0.0)
                Truth.assertThat(it.releaseYear).isEqualTo("")
                Truth.assertThat(it.genre).isEmpty()
                Truth.assertThat(it.poster).isEqualTo("")
            })
        }
    }

    @Test
    fun `deleteQueryFromHistory should delegate call to localDataSource`() = runTest {
        val query = "Batman"
        coEvery { localDataSource.deleteQueryFromHistory(query) } just Runs

        repository.deleteQueryFromHistory(query)

        coVerify(exactly = 1) { localDataSource.deleteQueryFromHistory(query) }
    }

    @Test
    fun `clearSearchHistory should delegate call to localDataSource`() = runTest {
        coEvery { localDataSource.clearSearchHistory() } just Runs

        repository.clearSearchHistory()

        coVerify(exactly = 1) { localDataSource.clearSearchHistory() }
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


    private val dummyPersonDto = PersonDto(
        id = 1,
        name = "Tom Hanks",
        knownForDepartment = "Acting",
        knownFor = listOf(dummyMovieDto, dummyMovieDto),
    )

}