package usecase

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.SearchRepository

class SearchByCountryUseCaseTest {

    private val searchRepository = mockk<SearchRepository>()
    lateinit var searchByCountryUseCase: SearchByCountryUseCase

    @Before
    fun setUp() {
        searchByCountryUseCase = SearchByCountryUseCase(searchRepository)
    }

    @Test
    fun `When search by valid country name and movies not found, then return empty list`() = runTest {
        // Given
        val countryName = "Egypt"
        coEvery { searchRepository.searchByCountry(countryName) } returns emptyList()

        // When
        val result = searchByCountryUseCase.invoke(countryName)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `When search by valid country name, then return list of movies relate to country`() = runTest {
        // Given
        val countryName = "Egypt"
        coEvery { searchRepository.searchByCountry(countryName) } returns getMoviesByCountry()

        // When
        val result = searchByCountryUseCase.invoke(countryName)

        // Then
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `When search by invalid country name, then return list of movies relate to country`() = runTest {
        // Given
        val countryName = "abcd"
        coEvery { searchRepository.searchByCountry(countryName) } returns emptyList()

        // When
        val result = searchByCountryUseCase.invoke(countryName)

        // Then
        assertThat(result).isEmpty()
    }

    fun getMoviesByCountry(): List<Movie> {
        return listOf(
            Movie(0, "a", 0f, "a", "a", emptyList(), "a"),
            Movie(1, "b", 0f, "b", "b", emptyList(), "b"),
            Movie(2, "c", 0f, "c", "c", emptyList(), "c"),
            Movie(3, "d", 0f, "d", "d", emptyList(), "d"),
            Movie(4, "e", 0f, "e", "e", emptyList(), "e")
        )
    }
}