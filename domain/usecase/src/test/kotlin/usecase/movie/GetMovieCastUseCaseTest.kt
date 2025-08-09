package usecase.movie

import com.berlin.entity.Actor
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository

class GetMovieCastUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getMovieCastUseCase: GetMovieCastUseCase =
        GetMovieCastUseCase(movieDetailsRepository)

    @Test
    fun `should return list of actors when movieId is valid`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieActors(MOVIE_ID) } returns actors

        // Act
        getMovieCastUseCase(MOVIE_ID)

        // Assert
        coVerify(exactly = 1) {
            movieDetailsRepository.getMovieActors(MOVIE_ID)
        }
    }

    @Test
    fun `should throw exception when repository fails to get actors`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieActors(MOVIE_ID) } throws Exception(FAILED_ACTORS)

        // Act
        assertThrows<Exception> {
            getMovieCastUseCase(MOVIE_ID)
        }

        // Assert
        coVerify(exactly = 1) {
            movieDetailsRepository.getMovieActors(MOVIE_ID)
        }
    }

    companion object {
        const val FAILED_ACTORS = "Failed to get actors"
        val actors = listOf(
            Actor(
                id = 90L,
                name = "Nadine Njeim",
                posterURL = "/test.jpg",
            ),
            Actor(
                id = 90L,
                name = "Nadine Al Rassi",
                posterURL = "/test.jpg",
            )
        )
        const val MOVIE_ID = 123L
    }
}