package usecase.mediadetails

import com.berlin.entity.Movie
import com.berlin.entity.ProductionCompany
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository
import usecase.movie.GetMovieDetailsUseCase

class GetMovieDetailsUseCaseTest {

    private val repository = mockk<MovieDetailsRepository>()
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `should return movie details when repository returns data`() = runTest {
        val movieId = 1L
        val expectedMovieDetails = movie

        coEvery { repository.getMovieDetails(movieId) } returns expectedMovieDetails

        val result = getMovieDetailsUseCase(movieId)

        assertThat(result).isEqualTo(expectedMovieDetails)
        coVerify(exactly = 1) { repository.getMovieDetails(movieId) }
    }

    @Test
    fun `should return null when movie not found`() = runTest {
        val movieId = 2L

        coEvery { repository.getMovieDetails(movieId) } returns null

        val result = getMovieDetailsUseCase(movieId)

        assertThat(result).isNull()
        coVerify(exactly = 1) { repository.getMovieDetails(movieId) }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        val movieId = 3L
        val exception = RuntimeException()

        coEvery { repository.getMovieDetails(movieId) } throws exception

        assertThrows<RuntimeException> {
            getMovieDetailsUseCase(movieId)
        }
    }


    companion object {
        val movie = Movie(
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
        )
    }
}
