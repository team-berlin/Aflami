package usecase.movie

import com.berlin.entity.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import repository.MovieRepository

class ContinueWatchingMovieUseCaseTest {

    private val repository: MovieRepository = mockk(relaxed = true)
    private lateinit var continueWatchingMovieUseCase: GetContinueWatchingMovieUseCase

    @Before
    fun setup() {
        continueWatchingMovieUseCase = GetContinueWatchingMovieUseCase(repository)
    }

    @Test
    fun `should return list of continue watching movies`() = runTest {
        coEvery { repository.getContinueWatchingMovies() } returns movies

        val result = continueWatchingMovieUseCase()

        assertEquals(movies, result)

        coVerify(exactly = 1) {
            repository.getContinueWatchingMovies()
        }
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
                productionCompanies = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList()
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
                productionCompanies = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList()
            )
        )
    }
}