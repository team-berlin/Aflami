package usecase.tvshow

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class SaveRecentTVShowsHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk(relaxed = true)
    private val saveRecentTVShowsHistoryUseCase: SaveRecentTVShowsHistoryUseCase =
        SaveRecentTVShowsHistoryUseCase(tvShowRepository)


    @Test
    fun `should call repository to save recent TV show query`() = runTest {
        // Arrange
        coEvery { tvShowRepository.saveRecentTVShowsHistory(QUERY) } returns Unit

        // Act
        val result = saveRecentTVShowsHistoryUseCase(QUERY)

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { tvShowRepository.saveRecentTVShowsHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.saveRecentTVShowsHistory(QUERY) } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { saveRecentTVShowsHistoryUseCase(QUERY) }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowRepository.saveRecentTVShowsHistory(QUERY) }
    }

    companion object {
        const val EXCEPTION = "Error to save recent tv show history"
        const val QUERY = "Breaking Bad"
    }
}