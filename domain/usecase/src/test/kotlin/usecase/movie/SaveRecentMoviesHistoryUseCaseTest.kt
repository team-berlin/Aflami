package usecase.movie

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class SaveRecentMoviesHistoryUseCaseTest {

    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val saveRecentMoviesHistoryUseCase: SaveRecentMoviesHistoryUseCase =
        SaveRecentMoviesHistoryUseCase(movieRepository)


    @Test
    fun `should call repository to save recent movie query`() = runTest {
        // Arrange
        coEvery { movieRepository.saveRecentMoviesHistory(QUERY) } returns Unit

        // Act
        saveRecentMoviesHistoryUseCase(QUERY)

        // Assert
        coVerify(exactly = 1) { movieRepository.saveRecentMoviesHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { movieRepository.saveRecentMoviesHistory(QUERY) } throws Exception()

        // Act
        assertThrows<Exception> { saveRecentMoviesHistoryUseCase(QUERY) }

        // Assert
        coVerify(exactly = 1) { movieRepository.saveRecentMoviesHistory(QUERY) }
    }

    companion object {
        const val QUERY = "Inception"
    }
}