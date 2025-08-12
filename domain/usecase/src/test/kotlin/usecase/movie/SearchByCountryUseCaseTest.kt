package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.MovieRepository

class SearchByCountryUseCaseTest {
    private val movieRepository: MovieRepository = mockk()
    private val searchMoviesByCountryUseCase: SearchMoviesByCountryUseCase =
        SearchMoviesByCountryUseCase(movieRepository)


    @Test
    fun `should call getMoviesByCountry in repository when invoke is called`() = runTest {
        // Arrange
        coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns MOVIES

        //Act
        val result = searchMoviesByCountryUseCase(QUERY, PAGE)

        // Assert
        assertThat(result).isEqualTo(MOVIES)
        coVerify { movieRepository.getMoviesByCountry(QUERY, PAGE) }
    }

    @Test
    fun `When search by valid country name and movies not found, then return empty list`() =
        runTest {
            // Arraneg
            coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns emptyList()

            // Act
            val result = searchMoviesByCountryUseCase(QUERY, PAGE)

            // Assert
            assertThat(result).isEmpty()
        }

    @Test
    fun `When search by valid country name, then return list of movies relate to country`() =
        runTest {
            // Arrange
            coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns MOVIES

            // Act
            val result = searchMoviesByCountryUseCase.invoke(QUERY, PAGE)

            // Assert
            assertThat(result).isEqualTo(MOVIES)
        }

    @Test
    fun `When search by invalid country name, then return list of movies relate to country`() =
        runTest {
            // Arrange
            coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns emptyList()

            // Act
            val result = searchMoviesByCountryUseCase.invoke(QUERY, PAGE)

            // Assert
            assertThat(result).isEmpty()
        }

    companion object {
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
        const val QUERY = "Inception"
        const val PAGE = 1
    }
}