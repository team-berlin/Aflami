package com.berlin.repository.impl

import com.berlin.repository.SearchRepositoryImpl
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MediaDto
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.berlin.repository.mapper.toMedia
import com.berlin.repository.util.QueryType
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class SearchRepositoryImplTest {

    private lateinit var remoteDataSource: SearchRemoteDataSource
    private lateinit var localDataSource: SearchLocalDataSource
    private lateinit var recentHistoryLocalDataSource: RecentHistoryLocalDataSource

    private lateinit var repository: SearchRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk(relaxed = true)
        recentHistoryLocalDataSource = mockk(relaxed = true)
        repository = SearchRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            recentHistoryLocalDataSource = recentHistoryLocalDataSource
        )

    }

    @Test
    fun `searchByCountry should return mapped movies when results are valid`() = runTest {
        // Given
        val country = "EG"
        val language = "en-US"
        val movieDto = dummyMovieDto

        val movie1 = movieDto.toLocal(
            query = country, page = 1, type = "COUNTRY",
            mediaType = "MOVIE"
        )
        val mapped = movie1.toDomain()

        val response = BaseResponse(results = listOf(movieDto, movieDto, movieDto))


        coEvery {
            localDataSource.getCachedSearch(
                country,
                QueryType.COUNTRY,
                pageSize = 20,
                page = 1
            )
        } returnsMany listOf(
            emptyList(),
            listOf(movie1, movie1, movie1)
        )

        coEvery {
            remoteDataSource.searchMoviesByCountry(
                country, language,
                page = 1
            )
        } returns response

        // When
        val result = repository.getMoviesByCountry(
            page = 1,
            query = country,
        )


        // Then
        Truth.assertThat(result).containsExactly(mapped, mapped, mapped)
    }

    @Test
    fun `searchByCountry should return empty list when results is null`() = runTest {
        // Given
        val country = "EG"
        val language = "en-US"
        val response = BaseResponse<MovieDto>(1, 10, results = null, 2)
        coEvery {
            remoteDataSource.searchMoviesByCountry(
                country, language,
                page = 1
            )
        } returns response

        // When
        val result = repository.getMoviesByCountry(country, 1)

        // Then
        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun `GIVEN valid cache WHEN getMediaByActorName is called THEN it returns cached data`() =
        runTest {
            // GIVEN
            val actorName = "Tom Hanks"
            val page = 1
            val cached = listOf(
                dummyMovieDto.toLocal(
                    actorName, QueryType.ACTOR.name, page, QueryType.MOVIE.name
                )
            )
            val expectedMedia = cached.map { it.toMedia() }

            coEvery {
                localDataSource.getCachedSearch(actorName, QueryType.ACTOR, 20, page)
            } returns cached

            // WHEN
            val result = repository.getMediaByActorName(actorName, page)

            // THEN
            assertThat(result).isEqualTo(expectedMedia)
            coVerify(exactly = 0) { remoteDataSource.searchMoviesByActor(any(), any(), any()) }
        }

    @Test
    fun `GIVEN remote returns null results WHEN getMediaByActorName is called THEN it returns empty list`() =
        runTest {
            // GIVEN
            val actorName = "Unknown Actor"
            val page = 1

        coEvery {
            localDataSource.getCachedSearch(actorName, QueryType.ACTOR, 20, page)
        } returns emptyList()

        coEvery {
            remoteDataSource.searchMoviesByActor(actorName, "en", page)
        } returns BaseResponse(results = null)

            // WHEN
            val result = repository.getMediaByActorName(actorName, page)

            // THEN
            assertThat(result).isEmpty()
    }

    @Test
    fun `GIVEN valid cached data WHEN getMediaByActorName is called THEN it returns cached data only`() =
        runTest {
            // GIVEN
            val actorName = "Tom Hanks"
            val page = 1
            val cachedItem =
                dummyMovieDto.toLocal(actorName, QueryType.ACTOR.name, page, QueryType.MOVIE.name)
            val expectedMedia = cachedItem.toMedia()

            coEvery {
                localDataSource.getCachedSearch(
                    actorName,
                    QueryType.ACTOR,
                    pageSize = 20,
                    page = page
                )
            } returns listOf(cachedItem)

            // WHEN
            val result = repository.getMediaByActorName(actorName, page)

            // THEN
            assertThat(result).containsExactly(expectedMedia)
        }

    @Test
    fun `GIVEN list with mixed departments WHEN getActingDepartment is called THEN only returns actors`() {
        // GIVEN
        val person1 = dummyPersonDto.copy(knownForDepartment = "Acting")
        val person2 = dummyPersonDto.copy(knownForDepartment = "Directing")
        val person3 = dummyPersonDto.copy(knownForDepartment = "Acting")
        val inputList = listOf(person1, person2, person3)

        // WHEN
        val result = repository.getActingDepartment(inputList)

        // THEN
        assertThat(result).containsExactly(person1, person3)
    }

    @Test
    fun `GIVEN persons with knownFor list WHEN getMediaByActorName is called THEN returns correct SearchingEntity list`() {
        // GIVEN
        val actorName = "Tom Hanks"
        val page = 1
        val expected = listOf(
            dummyMovieDto.toLocal(actorName, QueryType.ACTOR.name, page, QueryType.MOVIE.name)
        )

        // WHEN
        val result = repository.getMediaByActorName(actorName, page, listOf(dummyPersonDto))

        // THEN
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `GIVEN person with null knownFor WHEN getMediaByActorName is called THEN returns empty list`() {
        // GIVEN
        val person = dummyPersonDto.copy(knownFor = null)
        val actorName = "Someone"
        val page = 1

        // WHEN
        val result = repository.getMediaByActorName(actorName, page, listOf(person))

        // THEN
        assertThat(result).isEmpty()
    }


    @Test
    fun `getRecentSearchQueries should return list from localDataSource`() = runTest {
        val expected = listOf("Avengers", "Dark Knight")
        coEvery { recentHistoryLocalDataSource.getRecentSearchQueries() } returns expected

        val result = repository.getRecentSearchQueries()

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `saveRecentHistory should insert query as SearchingEntity`() = runTest {
        val query = "Spider Man"

        repository.saveRecentHistory(query)

        coVerify(exactly = 1) {
            recentHistoryLocalDataSource.insertQueryOnly(withArg {
                Truth.assertThat(it.type).isEqualTo("HISTORY")
            })
        }
    }

    @Test
    fun `deleteQueryFromHistory should delegate call to localDataSource`() = runTest {
        val query = "Batman"

        repository.deleteQueryFromHistory(query)

        coVerify(exactly = 1) { recentHistoryLocalDataSource.deleteQueryFromHistory(query) }
    }

    @Test
    fun `clearSearchHistory should delegate call to localDataSource`() = runTest {

        repository.clearSearchHistory()

        coVerify(exactly = 1) { recentHistoryLocalDataSource.clearSearchHistory() }
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

    private val dummyMediaDto = MediaDto(
        id = 101,
        title = "Epic Movie",
        genreIds = listOf(12, 18),
        posterPath = "/poster/path.jpg",
        releaseDate = "2023-05-15",
        popularity = 123.45,
        voteAverage = 8.7,
        mediaType = "MOVIE"
    )


    private val dummyPersonDto = PersonDto(
        id = 1,
        name = "Tom Hanks",
        knownForDepartment = "Acting",
        knownFor = listOf(dummyMediaDto),
    )
}