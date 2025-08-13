package usecase.movie

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class ClearMoviesSearchHistoryUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private val clearMoviesSearchHistoryUseCase: ClearMoviesSearchHistoryUseCase =
        ClearMoviesSearchHistoryUseCase(movieRepository)

    @Test
    fun `should call clearMovieSearchHistory once when invoked`() = runTest {
        // Arrange
        coEvery { movieRepository.clearMovieSearchHistory() } returns Unit

        // Act
        val result = clearMoviesSearchHistoryUseCase()

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) {
            movieRepository.clearMovieSearchHistory()
        }
    }

    @Test
    fun `should throw exception when repository fails to clear search history`() = runTest {
        // Arrange
        coEvery { movieRepository.clearMovieSearchHistory() } throws Exception(DB_ERROR)

        // Act
        val exception = assertThrows<Exception> {
            clearMoviesSearchHistoryUseCase()
        }

        // Assert
        assertThat(exception.message).isEqualTo(DB_ERROR)
        coVerify(exactly = 1) {
            movieRepository.clearMovieSearchHistory()
        }
    }

    companion object {
        const val DB_ERROR = "DB error"
    }
}