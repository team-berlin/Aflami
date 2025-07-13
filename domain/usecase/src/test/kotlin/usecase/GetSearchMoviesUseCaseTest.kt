package usecase

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import repository.SearchRepository

class GetSearchMoviesUseCaseTest {

    private val searchRepository = mockk<SearchRepository>()
    private lateinit var getSearchMoviesUseCase: GetSearchMoviesUseCase

    @Before
    fun setUp() {
        getSearchMoviesUseCase = GetSearchMoviesUseCase(searchRepository)
    }

    @Test
    fun `When search movies with valid query and no results, then return empty list`() = runTest {
        // Given
        val query = "Inception"
        val language = "en-US"
        coEvery { searchRepository.searchMovie(query, language) } returns emptyList()

        // When
        val result = getSearchMoviesUseCase.invoke(query, language)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `When search movies with valid query, then return list of movies`() = runTest {
        // Given
        val query = "Inception"
        val language = "en-US"
        coEvery { searchRepository.searchMovie(query, language) } returns getSampleMovies()

        // When
        val result = getSearchMoviesUseCase.invoke(query, language)

        // Then
        assertThat(result).isNotEmpty()
        assertThat(result.size).isEqualTo(5)
    }

    @Test
    fun `When search movies with random or invalid query, then return empty list`() = runTest {
        // Given
        val query = "abcdef"
        val language = "en-US"
        coEvery { searchRepository.searchMovie(query, language) } returns emptyList()

        // When
        val result = getSearchMoviesUseCase.invoke(query, language)

        // Then
        assertThat(result).isEmpty()
    }

    private fun getSampleMovies(): List<Movie> {
        return (1..5).map {
            Movie(
                id = it.toLong(),
                title = "Movie $it",
                rating = 8.0,
                releaseYear = LocalDate(2024, 7, it),
                genre = listOf(12, 18),
                poster = "/movie$it.jpg"
            )
        }
    }
}
