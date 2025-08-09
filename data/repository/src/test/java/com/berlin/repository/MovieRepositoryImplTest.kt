package com.berlin.repository

import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.fake.dummydata.DummyData.baseResponseMovieDetails
import com.berlin.repository.fake.dummydata.DummyData.baseResponsePersonDto
import com.berlin.repository.fake.dummydata.DummyData.movieEntity
import com.berlin.repository.fake.dummydata.DummyData.movieHomeEntityPopular
import com.berlin.repository.fake.dummydata.DummyData.movieHomeEntityTopRated
import com.berlin.repository.fake.dummydata.DummyData.movieHomeEntityUpcoming
import com.berlin.repository.fake.dummydata.DummyData.preferencesList
import com.berlin.repository.fake.dummydata.DummyData.recentlyWatchedMovieEntity
import com.berlin.repository.mapper.toDomain
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {


    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource = mockk(relaxed = true)
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource = mockk(relaxed = true)
    private val homeLocalDataSource: HomeLocalDataSource = mockk(relaxed = true)
    private val remoteDataSource: RemoteDataSource = mockk(relaxed = true)

    private lateinit var movieRepository: MovieRepositoryImpl

    @Before
    fun setUp() {

        movieRepository = MovieRepositoryImpl(
            recentlyWatchedLocalDataSource,
            recentHistoryLocalDataSource,
            homeLocalDataSource,
            remoteDataSource
        )
    }

    @Test
    fun `getContinueWatchingMovies should return a list of movies`() = runTest {

        val movieList = listOf(recentlyWatchedMovieEntity)
        val page = 1
        val categoryPreferences = preferencesList
        coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns categoryPreferences
        coEvery { recentlyWatchedLocalDataSource.getRecentlyWatchedMovie(page = page) } returns movieList

        val result = movieRepository.getContinueWatchingMovies(page)

        val expectedList = movieList.map { it.toDomain() }
        assertEquals(expectedList, result)
    }

    @Test
    fun `addContinueWatchingMovie should save a movie`() = runTest {

        val movie = movieEntity
        movieRepository.addContinueWatchingMovie(movie)

        coVerify { recentlyWatchedLocalDataSource.addRecentlyWatchedMovie(any()) }
    }

    @Test
    fun `getTopRatedMovies returns local data if not expired`() = runTest {

        val localMovies = listOf(movieHomeEntityTopRated)
        coEvery { homeLocalDataSource.getMoviesBySection(SectionHome.TOP_RATING) } returns localMovies

        val result = homeLocalDataSource.getMoviesBySection(SectionHome.TOP_RATING)

        assertEquals(1, result.size)
    }

    @Test
    fun `getTopRatedMovies returns remote data when local is empty`() = runTest {

        val movieList = baseResponseMovieDetails

        coEvery { homeLocalDataSource.getMoviesBySection(SectionHome.TOP_RATING) } returns emptyList()
        coEvery { remoteDataSource.getTopRatedMovies(1) } returns movieList
        coEvery { homeLocalDataSource.clearHomeScreenMovies(any()) } just Runs
        coEvery { homeLocalDataSource.addMovies(any()) } just Runs

        val result = movieRepository.getTopRatedMovies(1)

        assertEquals(1, result.size)
    }

    @Test
    fun `getUpComingMovies returns local data if not expired`() = runTest {

        val localMovies = listOf(movieHomeEntityUpcoming)
        coEvery { homeLocalDataSource.getMoviesBySection(SectionHome.UPCOMING) } returns localMovies

        val result = homeLocalDataSource.getMoviesBySection(SectionHome.UPCOMING)

        assertEquals(1, result.size)
    }

    @Test
    fun `getUpComingMovies returns remote data when local is empty`() = runTest {

        val movieList = baseResponseMovieDetails

        coEvery { homeLocalDataSource.getMoviesBySection(SectionHome.UPCOMING) } returns emptyList()
        coEvery { remoteDataSource.getUpComingMovies() } returns movieList
        coEvery { homeLocalDataSource.clearHomeScreenMovies(any()) } just Runs
        coEvery { homeLocalDataSource.addMovies(any()) } just Runs

        val result = movieRepository.getUpComingMovies()

        assertEquals(1, result.size)

    }

    @Test
    fun `getPopularMovies returns local data if not expired`() = runTest {
        val localMovies = listOf(movieHomeEntityPopular)
        coEvery { homeLocalDataSource.getMoviesBySection(SectionHome.POPULAR) } returns localMovies

        val result = homeLocalDataSource.getMoviesBySection(SectionHome.POPULAR)

        assertEquals(1, result.size)
    }

    @Test
    fun `getPopularMovies returns remote data when local is empty`() = runTest {

        val movieList = baseResponseMovieDetails

        coEvery { homeLocalDataSource.getMoviesBySection(SectionHome.POPULAR) } returns emptyList()
        coEvery { remoteDataSource.getPopularMovies() } returns movieList
        coEvery { homeLocalDataSource.clearHomeScreenMovies(any()) } just Runs
        coEvery { homeLocalDataSource.addMovies(any()) } just Runs

        val result = movieRepository.getPopularMovies()

        assertEquals(1, result.size)

    }

    @Test
    fun `getMoviesByMoods should return a list of movies`() = runTest {
        val moods = listOf(1, 2, 3)
        val movieList = baseResponseMovieDetails
        coEvery { remoteDataSource.getMoviesByMoodIds(moods) } returns movieList

        val result = movieRepository.getMoviesByMoods(moods)

        assertEquals(1, result.size)
    }

    @Test
    fun `getMoviesByCountry should return a list of movies`() = runTest {
        val query = "egypt"
        val page = 1
        val movieList = baseResponseMovieDetails
        val categoryPreferences = preferencesList

        coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns categoryPreferences
        coEvery { remoteDataSource.getMoviesByCountryName(query, page) } returns movieList

        val result = movieRepository.getMoviesByCountry(query, page)

        assertEquals(1, result.size)

    }

    @Test
    fun `getMoviesByActorName should return a list of movies`() = runTest {
        val actorName = "ahmed helmy"
        val page = 1
        val movieList = baseResponsePersonDto
        val categoryPreferences = preferencesList

        coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns categoryPreferences
        coEvery { remoteDataSource.getMoviesByActorName(actorName, page) } returns movieList

        val result = movieRepository.getMoviesByActorName(actorName, page)

        assertEquals(1,result.size)

    }

    @Test
    fun `getMovieByKeyWord should return a list of movies`() = runTest {
        val query = "keyword"
        val page = 1
        val movieList = baseResponseMovieDetails
        coEvery { remoteDataSource.getMoviesByKeyword(query, page) } returns movieList

        val result = movieRepository.getMovieByKeyWord(query, page)

        assertEquals(1, result.size)
        coVerify { remoteDataSource.getMoviesByKeyword(query, page) }
    }

    @Test
    fun `getRecentMoviesSearchQueries should return a list of queries`() = runTest {
        val queries = listOf("sponge pop", "dora")
        coEvery { recentHistoryLocalDataSource.getRecentSearchQueries() } returns queries

        val result = movieRepository.getRecentMoviesSearchQueries()

        assertEquals(queries, result)
        coVerify { recentHistoryLocalDataSource.getRecentSearchQueries() }
    }

    @Test
    fun `saveRecentMoviesHistory should save a query to recent history`() = runTest {
        val query = "sponge pop"
        coEvery { recentHistoryLocalDataSource.insertQueryOnly(any()) } just Runs

        movieRepository.saveRecentMoviesHistory(query)

        coVerify { recentHistoryLocalDataSource.insertQueryOnly(any()) }
    }

    @Test
    fun `deleteMovieQueryFromHistory should delete a query from recent history`() = runTest {
        val query = "sponge pop"
        coEvery { recentHistoryLocalDataSource.deleteQueryFromHistory(query) } just Runs

        movieRepository.deleteMovieQueryFromHistory(query)

        coVerify { recentHistoryLocalDataSource.deleteQueryFromHistory(query) }

    }

    @Test
    fun `clearMovieSearchHistory should clear the history`() = runTest {
        coEvery { recentHistoryLocalDataSource.clearSearchHistory() } just Runs

        movieRepository.clearMovieSearchHistory()

        coVerify { recentHistoryLocalDataSource.clearSearchHistory() }
    }

    @Test
    fun `isExpiredOrEmpty should return true if list is empty`() = runTest {

    }
}