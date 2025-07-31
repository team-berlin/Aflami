package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository

class GetSimilarMoviesUseCaseTest {
    private val movieDetailsRepository = mockk<MovieDetailsRepository>()
    private lateinit var getSimilarMoviesUseCase: GetSimilarMoviesUseCase

    @Before
    fun setUp() {
        getSimilarMoviesUseCase = GetSimilarMoviesUseCase((movieDetailsRepository))
    }

    @Test
    fun `should return media similar to media that returned when repository is called`() = runTest {
        coEvery {
            movieDetailsRepository.getSimilarMovies(
                MOVIE_ID,
            )
        } returns getSimilarMovie()

        val result = getSimilarMoviesUseCase.invoke(MOVIE_ID)
        val expected = getSimilarMovie()

        Truth.assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieDetailsRepository.getSimilarMovies(MOVIE_ID) }
    }

    @Test
    fun `should return empty list when media is not found`() = runTest {
        coEvery {
            movieDetailsRepository.getSimilarMovies(
                MOVIE_ID,
            )
        } returns emptyList()

        val result = getSimilarMoviesUseCase.invoke(MOVIE_ID)

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieDetailsRepository.getSimilarMovies(MOVIE_ID) }

    }

    @Test
    fun `should throw exception if movieDetailsRepository throw exception `() = runTest {
        coEvery {
            movieDetailsRepository.getSimilarMovies(
                MOVIE_ID,
            )
        } throws Exception()


        assertThrows<Exception> {
            getSimilarMoviesUseCase.invoke(MOVIE_ID)
        }
    }

    private fun getSimilarMovie(): List<Movie> {

        val movieList = mutableListOf<Movie>()
        for (i in 0..5) {
            movieList.add(
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
                )
            )
        }
        return movieList
    }

    companion object {
        const val MOVIE_ID = 0L
    }
}