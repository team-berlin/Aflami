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
import repository.MovieDetailsRepository

class GetMovieDetailsUseCaseTest {

    private val repository = mockk<MovieDetailsRepository>()
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `should return movie details when repository returns data`() = runTest {
        coEvery { repository.getMovieDetails( movie.id) } returns movie

        val result = getMovieDetailsUseCase( movie.id)

        assertThat(result).isEqualTo(movie)
        coVerify(exactly = 1) { repository.getMovieDetails(movie.id) }
    }

    @Test
    fun `should return null when movie not found`() = runTest {
        coEvery { repository.getMovieDetails(movie.id) } returns null

        val result = getMovieDetailsUseCase(movie.id)

        assertThat(result).isNull()
        coVerify(exactly = 1) { repository.getMovieDetails( movie.id) }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        coEvery { repository.getMovieDetails(movie.id) } throws Exception()

        assertThrows<Exception> {
            getMovieDetailsUseCase(movie.id)
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