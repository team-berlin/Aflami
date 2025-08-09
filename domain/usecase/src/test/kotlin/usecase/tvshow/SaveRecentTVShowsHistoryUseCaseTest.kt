package usecase.tvshow

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
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
        saveRecentTVShowsHistoryUseCase(QUERY)

        // Assert
        coVerify(exactly = 1) { tvShowRepository.saveRecentTVShowsHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.saveRecentTVShowsHistory(QUERY) } throws Exception()

        // Act
        assertThrows<Exception> { saveRecentTVShowsHistoryUseCase(QUERY) }

        // Assert
        coVerify(exactly = 1) { tvShowRepository.saveRecentTVShowsHistory(QUERY) }
    }

    companion object {
        const val QUERY = "Breaking Bad"
    }
}