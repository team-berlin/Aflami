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

class AddContinueWatchingMovieUseCaseTest {
    private val movieRepository: MovieRepository = mockk()
    private val addContinueWatchingMovieUseCase: AddContinueWatchingMovieUseCase =
        AddContinueWatchingMovieUseCase(movieRepository)


    @Test
    fun `should call addContinueWatchingMovie once when movie is correct`() = runTest {
        // Arrange
        coEvery { movieRepository.addContinueWatchingMovie(TEST_MOVIE) } returns Unit

        // Act
        val result = addContinueWatchingMovieUseCase(TEST_MOVIE)

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) {
            movieRepository.addContinueWatchingMovie(TEST_MOVIE)
        }
    }

    @Test
    fun `should throw exception when repository fails to add movie`() = runTest {
        // Arrange
        coEvery { movieRepository.addContinueWatchingMovie(TEST_MOVIE) } throws
                Exception(DB_ERROR)

        // Act
        val exception = assertThrows<Exception> {
            addContinueWatchingMovieUseCase(TEST_MOVIE)
        }

        // Assert
        assertThat(exception.message).isEqualTo(DB_ERROR)
        coVerify(exactly = 1) {
            movieRepository.addContinueWatchingMovie(TEST_MOVIE)
        }
    }

    companion object {
        val TEST_MOVIE = Movie(
            id = 90L,
            title = "Test Movie",
            rating = 7.9,
            releaseDate = "1/12/2001",
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
        const val DB_ERROR = "DB error"
    }
}