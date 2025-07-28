package usecase.movie

import com.berlin.entity.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class GetPopularMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `should return popular movies when repository returns data`() = runTest {
        coEvery { repository.getPopularMovies() } returns movies

        val result = getPopularMoviesUseCase()

        assertEquals(movies, result)
        coVerify(exactly = 1) { repository.getPopularMovies() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
         coEvery { repository.getPopularMovies() } throws Exception(EXCEPTION)

        assertThrows<Exception> {
            getPopularMoviesUseCase()
        }

        coVerify(exactly = 1) { repository.getPopularMovies() }
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
        const val EXCEPTION = "Network error"
    }

}