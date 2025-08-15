package usecase.movie

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class SaveRecentMoviesHistoryUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private val saveRecentMoviesHistoryUseCase: SaveRecentMoviesHistoryUseCase =
        SaveRecentMoviesHistoryUseCase(movieRepository)


    @Test
    fun `should call repository to save recent movie query when invoked`() = runTest {
        // Arrange
        coEvery { movieRepository.saveRecentMoviesHistory(QUERY) } returns Unit

        // Act
        val result = saveRecentMoviesHistoryUseCase(QUERY)

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { movieRepository.saveRecentMoviesHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { movieRepository.saveRecentMoviesHistory(QUERY) } throws
                Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> { saveRecentMoviesHistoryUseCase(QUERY) }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { movieRepository.saveRecentMoviesHistory(QUERY) }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to movie history"
        const val QUERY = "Inception"
    }
}