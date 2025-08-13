package usecase.movie

import com.berlin.entity.Genre
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository

class GetMovieGenresUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getMovieGenresUseCase: GetMovieGenresUseCase =
        GetMovieGenresUseCase(movieDetailsRepository)

    @Test
    fun `should return list of genres when calling repository`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieGenres() } returns GENRES

        //Act
        val result = getMovieGenresUseCase()

        // Assert
        assertThat(result).isEqualTo(GENRES)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieGenres() }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieGenres() } throws Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> {
            getMovieGenresUseCase()
        }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieGenres() }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to get movie genres"
        val GENRES = listOf(
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Drama")
        )
    }
}