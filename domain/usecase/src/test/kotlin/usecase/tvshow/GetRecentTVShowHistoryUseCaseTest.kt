package usecase.tvshow

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class GetRecentTVShowHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk()
    private lateinit var getRecentTVShowHistoryUseCase: GetRecentTVShowHistoryUseCase

    @Before
    fun setUp() {
        getRecentTVShowHistoryUseCase = GetRecentTVShowHistoryUseCase(tvShowRepository)
    }

    @Test
    fun `should return list of recent TV show search queries when calling repository`() = runTest {
        coEvery { tvShowRepository.getRecentTVShowsSearchQueries() } returns RECENT_QUERIES

        val callResult = getRecentTVShowHistoryUseCase()

        assertThat(callResult).isEqualTo(RECENT_QUERIES)
        coVerify(exactly = 1) { tvShowRepository.getRecentTVShowsSearchQueries() }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        coEvery { tvShowRepository.getRecentTVShowsSearchQueries() } throws Exception()

        assertThrows<Exception> {
            getRecentTVShowHistoryUseCase()
        }
    }

    companion object {
        val RECENT_QUERIES = listOf("Breaking Bad", "The Office")
    }
}