package usecase.movie

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class GetRecentMoviesHistoryUseCaseTest {
    private var movieRepository: MovieRepository = mockk()
    private val getRecentMoviesHistoryUseCase: GetRecentMoviesHistoryUseCase =
        GetRecentMoviesHistoryUseCase(movieRepository)

    @Test
    fun `invoke should return recent search queries from repository`() = runTest {
        // Arrange
        coEvery { movieRepository.getRecentMoviesSearchQueries() } returns QUERIES

        // Act
        val result = getRecentMoviesHistoryUseCase()

        // Assert
        assertThat(result).isEqualTo(QUERIES)
    }

    @Test
    fun `invoke should throw exception when repository fails`() = runTest {
        // Arrange
        coEvery { movieRepository.getRecentMoviesSearchQueries() } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getRecentMoviesHistoryUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { movieRepository.getRecentMoviesSearchQueries() }
    }

    companion object {
        const val EXCEPTION = "Failed to fetch recent search queries"
        val QUERIES = listOf("Inception", "Matrix", "Oppenheimer")
    }
}