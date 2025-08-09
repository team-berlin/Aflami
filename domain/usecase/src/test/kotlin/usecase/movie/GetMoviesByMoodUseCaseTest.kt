package usecase.movie

import com.berlin.entity.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class GetMoviesByMoodUseCaseTest {
    private val movieRepository: MovieRepository = mockk()
    private val getMoviesByMoodUseCase: GetMoviesByMoodUseCase =
        GetMoviesByMoodUseCase(movieRepository)

    @Test
    fun `should return list of int when list of int is valid`() = runTest {
        // Arrange
        coEvery { movieRepository.getMoviesByMoods(GENRES) } returns MOVIES

        // Act
        getMoviesByMoodUseCase(GENRES)

        // Assert
        coVerify(exactly = 1) {
            movieRepository.getMoviesByMoods(GENRES)
        }
    }

    @Test
    fun `should throw exception when repository fails `() = runTest {
        // Arrange
        coEvery { movieRepository.getMoviesByMoods(GENRES) } throws Exception()

        // Act
        assertThrows<Exception> { getMoviesByMoodUseCase(GENRES) }

        // Assert
        coVerify(exactly = 1) {
            movieRepository.getMoviesByMoods(GENRES)
        }
    }

    companion object {

        val GENRES = listOf(2, 46, 394, 38)
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
    }

}