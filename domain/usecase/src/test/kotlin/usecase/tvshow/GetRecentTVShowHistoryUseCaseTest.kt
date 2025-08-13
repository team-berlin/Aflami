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
    private val getRecentTVShowHistoryUseCase: GetRecentTVShowHistoryUseCase =
        GetRecentTVShowHistoryUseCase(tvShowRepository)

    @Test
    fun `should return list of recent TV show search queries when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowRepository.getRecentTVShowsSearchQueries() } returns RECENT_QUERIES

        // Act
        val result = getRecentTVShowHistoryUseCase()

        // Assert
        assertThat(result).isEqualTo(RECENT_QUERIES)
        coVerify(exactly = 1) { tvShowRepository.getRecentTVShowsSearchQueries() }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.getRecentTVShowsSearchQueries() } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getRecentTVShowHistoryUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowRepository.getRecentTVShowsSearchQueries() }
    }

    companion object {
        const val EXCEPTION = "Error to get recent history"
        val RECENT_QUERIES = listOf("Breaking Bad", "The Office")
    }
}