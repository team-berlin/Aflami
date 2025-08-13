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
import usecase.movie.GetMovieGenresUseCaseTest.Companion.ERROR_MESSAGE

class GetPopularMoviesUseCaseTest {

    private var movieRepository: MovieRepository = mockk()
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase =
        GetPopularMoviesUseCase(movieRepository)

    @Test
    fun `should return popular movies when repository returns data`() = runTest {
        // Arrange
        coEvery { movieRepository.getPopularMovies() } returns movies

        //Act
        val result = getPopularMoviesUseCase()

        // Assert
        assertThat(result).isEqualTo(movies)
        coVerify(exactly = 1) { movieRepository.getPopularMovies() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { movieRepository.getPopularMovies() } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getPopularMoviesUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { movieRepository.getPopularMovies() }
    }

    companion object {
        val movies = listOf(
            Movie(
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
            ),
            Movie(
                id = 24L,
                title = "Test Movie two",
                rating = 9.7,
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
        )
        const val EXCEPTION = "Network error"
    }

}