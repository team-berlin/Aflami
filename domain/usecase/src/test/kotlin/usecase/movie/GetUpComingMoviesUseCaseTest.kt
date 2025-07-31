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

class GetUpComingMoviesUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private lateinit var getUpComingMoviesUseCase: GetUpComingMoviesUseCase

    @Before
    fun setUp() {
        getUpComingMoviesUseCase = GetUpComingMoviesUseCase(movieRepository)
    }

    @Test
    fun `should return list of movies when calling repository`() = runTest {
        coEvery { movieRepository.getUpComingMovies() } returns MOVIES

        val callResult = getUpComingMoviesUseCase()

        assertThat(callResult).isEqualTo(MOVIES)
        coVerify(exactly = 1) { movieRepository.getUpComingMovies() }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        coEvery { movieRepository.getUpComingMovies() } throws Exception()

        assertThrows<Exception> {
            getUpComingMoviesUseCase()
        }
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