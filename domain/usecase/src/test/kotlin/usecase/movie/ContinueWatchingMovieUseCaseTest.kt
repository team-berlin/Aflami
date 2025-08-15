package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class ContinueWatchingMovieUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private val continueWatchingMovieUseCase: ContinueWatchingMovieUseCase =
        ContinueWatchingMovieUseCase(movieRepository)

    @Test
    fun `should return list of continue watching movies when invoked`() = runTest {
        // Arrange
        coEvery { movieRepository.getContinueWatchingMovies(PAGE) } returns movies

        // Act
        val result = continueWatchingMovieUseCase(PAGE)

        // Assert
        assertThat(result).isEqualTo(movies)
        coVerify(exactly = 1) {
            movieRepository.getContinueWatchingMovies(PAGE)
        }
    }

    @Test
    fun `should throw exception when repository fails to get continue watching movies`() = runTest {
        // Arrange
        coEvery { movieRepository.getContinueWatchingMovies(PAGE) } throws
                Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> {
            continueWatchingMovieUseCase(PAGE)
        }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) {
            movieRepository.getContinueWatchingMovies(PAGE)
        }
    }

    companion object {
         const val ERROR_MESSAGE = "Failed to get continue watching movies"
        const val PAGE = 1
        val movies = listOf(
            Movie(
                id = 90L,
                title = "Test Movie",
                rating = 7.9,
                releaseDate = "1/12/2020",
                posterURL = "/test.jpg",
                screenShot = "/test.jpg",
                description = "This is the test movie",
                genres = emptyList(),
                duration = 3,
                hasVideo = false,
                companyProductions = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList(),
                reviews = emptyList(),
                isFavourite = false
            ),
            Movie(
                id = 24L,
                title = "Test Movie two",
                rating = 9.7,
                releaseDate = "1/12/2021",
                posterURL = "/test.jpg",
                screenShot = "/test.jpg",
                description = "This is the test movie",
                genres = emptyList(),
                duration = 3,
                hasVideo = false,
                companyProductions = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList(),
                reviews = emptyList(),
                isFavourite = false,
            )
        )
    }
}