package usecase.mediadetails

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
        val language = "en-US"
        val expectedMovieDetails = getFakeMovieDetails()

        coEvery { repository.getMovieDetails(movieId, language) } returns expectedMovieDetails

        val result = getMovieDetailsUseCase(movieId, language)

        assertThat(result).isEqualTo(expectedMovieDetails)
        coVerify(exactly = 1) { repository.getMovieDetails(movieId, language) }
    }

    @Test
    fun `should return null when movie not found`() = runTest {
        val movieId = 2L
        val language = "en-US"

        coEvery { repository.getMovieDetails(movieId, language) } returns null

        val result = getMovieDetailsUseCase(movieId, language)

        assertThat(result).isNull()
        coVerify(exactly = 1) { repository.getMovieDetails(movieId, language) }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Given
        val movieId = 3L
        val language = "en-US"
        val exception = RuntimeException()

        coEvery { repository.getMovieDetails(movieId, language) } throws exception

        // When + Then
        assertThrows<RuntimeException>{
            getMovieDetailsUseCase(movieId, language)
        }
    }


    private fun getFakeMovieDetails(): MovieDetails {
        return MovieDetails(
            id = 1L,
            title = "Inception",
            overview = "A thief who steals corporate secrets through dream-sharing technology.",
            posterUrl = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            releaseDate = "2010-07-16",
            rating = 8.8,
            runtime = 148,
            genres = listOf(
                GenreEntity(id = 28, name = "Action"),
                GenreEntity(id = 878, name = "Science Fiction")
            ),
            productionCompanies = listOf(
                ProductionCompany(
                    id = 1,
                    name = "Legendary Pictures",
                    poster = "https://image.tmdb.org/t/p/w500/company_logo.jpg",
                    originCountry = "US",
                )
            ),
            originCountry = "US",
            duration = "5",
            hasVideo = true
        )
    }
}
