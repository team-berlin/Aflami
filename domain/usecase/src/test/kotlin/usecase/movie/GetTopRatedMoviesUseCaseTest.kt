package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class GetTopRatedMoviesUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase =
        GetTopRatedMoviesUseCase(movieRepository)

    @Test
    fun `should return list of movies when calling repository`() = runTest {
        // Arrange
        coEvery { movieRepository.getTopRatedMovies(PAGE) } returns MOVIES

        // Act
        getTopRatedMoviesUseCase(PAGE)

        // Assert
        coVerify(exactly = 1) { movieRepository.getTopRatedMovies(PAGE) }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        // Arrange
        coEvery { movieRepository.getTopRatedMovies(PAGE) } throws Exception()

        // Act
        assertThrows<Exception> {
            getTopRatedMoviesUseCase(PAGE)
        }

        // Assert
        coVerify(exactly = 1) { movieRepository.getTopRatedMovies(PAGE) }
    }

    companion object {
        const val PAGE = 1
        val MOVIES = listOf(
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
    }
}