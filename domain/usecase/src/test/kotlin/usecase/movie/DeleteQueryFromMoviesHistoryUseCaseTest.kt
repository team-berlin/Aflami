package usecase.movie

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository
import usecase.movie.AddContinueWatchingMovieUseCaseTest.Companion.DB_ERROR

class DeleteQueryFromMoviesHistoryUseCaseTest {
    private val movieRepository: MovieRepository = mockk()
    private val deleteQueryFromMoviesHistoryUseCase: DeleteQueryFromMoviesHistoryUseCase =
        DeleteQueryFromMoviesHistoryUseCase(movieRepository)


    @Test
    fun `invoke should call deleteQueryFromHistory on repository`() = runTest {
        // Arrange
        coEvery { movieRepository.deleteMovieQueryFromHistory(QUERY) } returns Unit

        // Act
        val result = deleteQueryFromMoviesHistoryUseCase(QUERY)

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify { movieRepository.deleteMovieQueryFromHistory(QUERY) }
    }

    @Test
    fun `should throw exception when repository fails to delete query from history`() = runTest {
        // Arrange
        coEvery { movieRepository.deleteMovieQueryFromHistory(QUERY) } throws
                Exception(DB_ERROR)

        // Act
        assertThrows<Exception> {
            deleteQueryFromMoviesHistoryUseCase(QUERY)
        }

        // Assert
        coVerify(exactly = 1) {
            movieRepository.deleteMovieQueryFromHistory(QUERY)
        }
    }

    companion object {
        const val QUERY = "Matrix"
    }
}