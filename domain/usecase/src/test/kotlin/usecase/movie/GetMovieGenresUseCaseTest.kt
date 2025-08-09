package usecase.movie

import com.berlin.entity.Genre
import io.mockk.mockk
import repository.MovieDetailsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class GetMovieGenresUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getMovieGenresUseCase: GetMovieGenresUseCase =
        GetMovieGenresUseCase(movieDetailsRepository)

    @Test
    fun `should return list of genres when calling repository`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieGenres() } returns GENRES

        //Act
        getMovieGenresUseCase()

        // Assert
        coVerify(exactly = 1) { movieDetailsRepository.getMovieGenres() }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieGenres() } throws Exception()

        // Act
        assertThrows<Exception> {
            getMovieGenresUseCase()
        }

        // Assert
        coVerify(exactly = 1) { movieDetailsRepository.getMovieGenres() }
    }

    companion object {
        val GENRES = listOf(
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Drama")
        )
    }
}