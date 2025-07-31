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
    private lateinit var searchByCountryUseCase: SearchByCountryUseCase

    @Before
    fun setUp() {
        searchByCountryUseCase = SearchByCountryUseCase(movieRepository)
    }

    @Test
    fun `should call getMoviesByCountry in repository when invoke is called`() = runTest {
        coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns emptyList()

        searchByCountryUseCase(QUERY, PAGE)

        coVerify { movieRepository.getMoviesByCountry(QUERY, PAGE) }
    }

    @Test
    fun `When search by valid country name and movies not found, then return empty list`() =
        runTest {
            coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns emptyList()

            val result = searchByCountryUseCase(QUERY, PAGE)

            assertThat(result).isEmpty()
        }

    @Test
    fun `When search by valid country name, then return list of movies relate to country`() =
        runTest {
            coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns MOVIES

            val result = searchByCountryUseCase.invoke(QUERY, PAGE)

            assertThat(result).containsExactlyElementsIn(MOVIES)
        }

    @Test
    fun `When search by invalid country name, then return list of movies relate to country`() =
        runTest {
            coEvery { movieRepository.getMoviesByCountry(QUERY, PAGE) } returns emptyList()

            val result = searchByCountryUseCase.invoke(QUERY, PAGE)

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
        const val QUERY = "Inception"
        const val PAGE = 1
    }
}