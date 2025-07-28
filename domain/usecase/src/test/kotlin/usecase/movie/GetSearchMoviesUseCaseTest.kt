package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class GetSearchMoviesUseCaseTest {
    private val searchRepository: MovieRepository = mockk()
    private lateinit var getSearchMoviesUseCase: GetSearchMoviesUseCase

    @Before
    fun setUp() {
        getSearchMoviesUseCase = GetSearchMoviesUseCase(searchRepository)
    }

    @Test
    fun `should return movies when repository returns result`() = runTest {
        coEvery { searchRepository.searchMovie(QUERY, PAGE) } returns movies

        // When
        val result = getSearchMoviesUseCase(QUERY, PAGE)

        // Then
        assertThat(result).isEqualTo(movies)
        coVerify(exactly = 1) { searchRepository.searchMovie(QUERY, PAGE) }
    }

    @Test
    fun `should return empty list when repository returns nothing`() = runTest {
        coEvery { searchRepository.searchMovie(QUERY, PAGE) } returns emptyList()

        val result = getSearchMoviesUseCase(QUERY, PAGE)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { searchRepository.searchMovie(QUERY, PAGE) }
    }

    @Test
    fun `should throw exception when repository throws`() = runTest {

        coEvery { searchRepository.searchMovie(QUERY, PAGE) } throws Exception(EXCEPTION)

        val thrown = assertThrows<Exception> {
            getSearchMoviesUseCase(QUERY, PAGE)
        }

        assertThat(thrown).hasMessageThat().isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { searchRepository.searchMovie(QUERY, PAGE) }
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
        const val PAGE = 1
        const val QUERY = "Movie"

        const val EXCEPTION = "Network error"
    }
}