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

class GetSearchMoviesUseCaseTest {
    private val movieRepository: MovieRepository = mockk()
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase =
        GetSearchMoviesUseCase(movieRepository)

    @Test
    fun `should return movies when repository returns result`() = runTest {
        // Arrange
        coEvery { movieRepository.getMovieByKeyWord(QUERY, PAGE) } returns movies

        // Act
        val result = getSearchMoviesUseCase(QUERY, PAGE)

        // Assert
        assertThat(result).isEqualTo(movies)
        coVerify(exactly = 1) { movieRepository.getMovieByKeyWord(QUERY, PAGE) }
    }

    @Test 
    fun `should return empty list when movie is not found`() = runTest {
        // Arrange
        coEvery { movieRepository.getMovieByKeyWord(QUERY, PAGE) } returns emptyList()

        // Act
        val result = getSearchMoviesUseCase(QUERY, PAGE)

        // Assert
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMovieByKeyWord(QUERY, PAGE) }
    }

    @Test
    fun `should throw exception when repository throws`() = runTest {
        // Arrange
        coEvery { movieRepository.getMovieByKeyWord(QUERY, PAGE) } throws
                Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getSearchMoviesUseCase(QUERY, PAGE) }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { movieRepository.getMovieByKeyWord(QUERY, PAGE) }
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
        const val PAGE = 1
        const val QUERY = "Movie"

        const val EXCEPTION = "Network error"
    }
}