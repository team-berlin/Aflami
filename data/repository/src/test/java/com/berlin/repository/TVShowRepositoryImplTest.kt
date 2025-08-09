package com.berlin.repository

import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.fake.dummydata.DummyData.baseResponseTVShowDetails
import com.berlin.repository.fake.dummydata.DummyData.mediaPreferencesList
import com.berlin.repository.fake.dummydata.DummyData.recentlyWatchedTvShowEntity
import com.berlin.repository.fake.dummydata.DummyData.tvShowEntity
import com.berlin.repository.fake.dummydata.DummyData.tvShowHomeEntity
import com.berlin.repository.fake.dummydata.DummyData.tvShowHomeEntityPopular
import com.berlin.repository.fake.dummydata.DummyData.tvShowHomeEntityTopRated
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

class TVShowRepositoryImplTest {

    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource =
        mockk(relaxed = true)
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource = mockk(relaxed = true)
    private val homeLocalDataSource: HomeLocalDataSource = mockk(relaxed = true)
    private val remoteDataSource: RemoteDataSource = mockk(relaxed = true)
    private lateinit var tvShowRepository: TVShowRepositoryImpl

    @Before
    fun setUp() {
        tvShowRepository = TVShowRepositoryImpl(
            recentlyWatchedLocalDataSource,
            recentHistoryLocalDataSource,
            homeLocalDataSource,
            remoteDataSource
        )
    }


    @Test
    fun `getContinueWatchingTVShow should return a list of tvShow`() = runTest {

        val tvShowList = listOf(recentlyWatchedTvShowEntity)
        val page = 1
        val categoryPreferences = mediaPreferencesList
        coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns categoryPreferences
        coEvery { recentlyWatchedLocalDataSource.getRecentlyWatchedTvShow(page = page) } returns tvShowList

        val result = tvShowRepository.getContinueWatchingTVShows(page)

        val expectedList = tvShowList.map { it.toDomain() }
        assertEquals(expectedList, result)
    }

    @Test
    fun `addContinueWatchingTVShow should save a tvShow`() = runTest {

        val tvShow = tvShowEntity
        coEvery { recentlyWatchedLocalDataSource.addRecentlyWatchedTvShow(any()) } just Runs

        tvShowRepository.addContinueWatchingTVShow(tvShow)

        coVerify { recentlyWatchedLocalDataSource.addRecentlyWatchedTvShow(any()) }
    }

    @Test
    fun `getTopRatedTVShows returns local data if not expired`() = runTest {

        val localTVShows = listOf(
            tvShowHomeEntity.copy(addedAt = System.currentTimeMillis())
        )
        coEvery { homeLocalDataSource.getTVShowsBySection(SectionHome.TOP_RATING) } returns localTVShows

        val result = tvShowRepository.getTopRatedTVShows(1)

        assertEquals(localTVShows.map { it.toDomain() }, result)

    }

    @Test
    fun `getTopRatedTVShows returns remote data when local is empty`() = runTest {

        val tvShowList = baseResponseTVShowDetails

        coEvery { homeLocalDataSource.getTVShowsBySection(SectionHome.TOP_RATING) } returns emptyList()
        coEvery { remoteDataSource.getTopRatedTV(1) } returns tvShowList
        coEvery { homeLocalDataSource.clearHomeScreenTVShows(any()) } just Runs
        coEvery { homeLocalDataSource.addTVShows(any()) } just Runs

        val result = tvShowRepository.getTopRatedTVShows(1)

        assertEquals(1, result.size)
    }

    @Test
    fun `getPopularTVShow returns local data if not expired`() = runTest {

        val localTVShows = listOf(
            tvShowHomeEntity.copy(addedAt = System.currentTimeMillis())
        )
        coEvery { homeLocalDataSource.getTVShowsBySection(SectionHome.POPULAR) } returns localTVShows

        val result = tvShowRepository.getPopularTVShows()

        assertEquals(localTVShows.map { it.toDomain() }, result)
    }

    @Test
    fun `getPopularTVShow returns remote data when local is empty`() = runTest {

        val remoteTVShows = baseResponseTVShowDetails
        coEvery { homeLocalDataSource.getTVShowsBySection(SectionHome.POPULAR) } returns emptyList()
        coEvery { remoteDataSource.getPopularTVShows() } returns remoteTVShows
        coEvery { homeLocalDataSource.clearHomeScreenTVShows(any()) } just Runs
        coEvery { homeLocalDataSource.addTVShows(any()) } just Runs

        val result = tvShowRepository.getPopularTVShows()

        assertEquals(remoteTVShows.results?.map { it.toDomain() }, result)
    }


    @Test
    fun `searchTVShow should return sorted list of tvShows`() = runTest {
        val query = "sponge pop"
        val page = 1
        val categoryPreferences = mediaPreferencesList
        val tvShowList = baseResponseTVShowDetails

        coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns categoryPreferences
        coEvery { remoteDataSource.getTVShowsByKeyword(query, page) } returns tvShowList

        val result = tvShowRepository.searchTVShow(query, page)

        val expected= tvShowList.results?.map { it.toDomain() }
        assertEquals(expected?.size, result.size)

    }

    @Test
    fun `getRecentTVShowSearchQueries should return a list of queries`() = runTest {
        val queries = listOf("sponge pop", "dora")
        coEvery { recentHistoryLocalDataSource.getRecentSearchQueries() } returns queries

        val result = tvShowRepository.getRecentTVShowsSearchQueries()

        assertEquals(queries, result)
        coVerify { recentHistoryLocalDataSource.getRecentSearchQueries() }
    }

    @Test
    fun `saveRecentTVShowHistory should save a query to recent history`() = runTest {
        val query = "sponge pop"
        coEvery { recentHistoryLocalDataSource.insertQueryOnly(any()) } just Runs

        tvShowRepository.saveRecentTVShowsHistory(query)

        coVerify { recentHistoryLocalDataSource.insertQueryOnly(any()) }
    }

    @Test
    fun `deleteTVShowQueryFromHistory should delete a query from recent history`() = runTest {
        val query = "sponge pop"
        coEvery { recentHistoryLocalDataSource.deleteQueryFromHistory(query) } just Runs

        tvShowRepository.deleteTVShowQueryFromHistory(query)

        coVerify { recentHistoryLocalDataSource.deleteQueryFromHistory(query) }

    }

    @Test
    fun `clearTVShowSearchHistory should clear the history`() = runTest {
        coEvery { recentHistoryLocalDataSource.clearSearchHistory() } just Runs

        tvShowRepository.clearTVShowSearchHistory()

        coVerify { recentHistoryLocalDataSource.clearSearchHistory() }
    }



}
