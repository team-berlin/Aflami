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

class SearchByCountryUseCaseTest {

    private val searchRepository = mockk<SearchRepository>()
    private lateinit var searchByCountryUseCase: SearchByCountryUseCase

    @Before
    fun setUp() {
        searchByCountryUseCase = SearchByCountryUseCase(searchRepository)
    }

    @Test
    fun `When search by valid country name and movies not found, then return empty list`() = runTest {
        // Given
        val countryName = "Eg"
        val language = "en-US"
        coEvery { searchRepository.getMoviesByCountry(countryName, language) } returns emptyList()

        // When
        val result = searchByCountryUseCase.invoke(countryName, language)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `When search by valid country name, then return list of movies relate to country`() = runTest {
        // Given
        val countryName = "Eg"
        val language = "en-US"
        coEvery { searchRepository.getMoviesByCountry(countryName, language) } returns getMoviesByCountry()

        // When
        val result = searchByCountryUseCase.invoke(countryName, language)

        // Then
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `When search by invalid country name, then return list of movies relate to country`() = runTest {
        // Given
        val countryName = "abcd"
        val language = "en-US"
        coEvery { searchRepository.getMoviesByCountry(countryName, language) } returns emptyList()

        // When
        val result = searchByCountryUseCase.invoke(countryName, language)

        // Then
        assertThat(result).isEmpty()
    }

    private fun getMoviesByCountry(): List<Movie> {
        return (0..5).map {
            Movie(it.toLong(), "Filmy", 5.0, LocalDate(2024, 6, 23), emptyList(), "path")
        }
    }
}