package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository

class GetSimilarMoviesUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase =
        GetSimilarMoviesUseCase(movieDetailsRepository)

    @Test
    fun `should return media similar to media that returned when repository is called`() = runTest {
        // Arrange
        coEvery {
            movieDetailsRepository.getSimilarMovies(MOVIE_ID)
        } returns MOVIES

        // Act
        val result = getSimilarMoviesUseCase.invoke(MOVIE_ID)

        // Assert
        assertThat(result).isEqualTo(MOVIES)
        coVerify(exactly = 1) { movieDetailsRepository.getSimilarMovies(MOVIE_ID) }
    }

    @Test
    fun `should return empty list when media is not found`() = runTest {
        // Arrange
        coEvery {
            movieDetailsRepository.getSimilarMovies(MOVIE_ID)
        } returns emptyList()

        // Act
        getSimilarMoviesUseCase.invoke(MOVIE_ID)

        // Assert
        coVerify(exactly = 1) { movieDetailsRepository.getSimilarMovies(MOVIE_ID) }

    }

    @Test
    fun `should throw exception if movieDetailsRepository throw exception`() = runTest {
        // Arrange
        coEvery {
            movieDetailsRepository.getSimilarMovies(MOVIE_ID,)
        } throws Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> {
            getSimilarMoviesUseCase(MOVIE_ID)
        }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { movieDetailsRepository.getSimilarMovies(MOVIE_ID) }
    }

    companion object {
        val MOVIES = listOf(
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
        const val ERROR_MESSAGE = "Failed to get movie details"
        const val MOVIE_ID = 0L
    }
}