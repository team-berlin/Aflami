package usecase

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
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
    fun `should call getMoviesByCountry in repository when invoke is called`() = runTest {
        // Given
        val query = "Eg"
        coEvery { searchRepository.getMoviesByCountry(query, 1) } returns emptyList()

        // When
        searchByCountryUseCase(query, 1)

        // Then
        coVerify { searchRepository.getMoviesByCountry(query, 1) }
    }

    @Test
    fun `When search by valid country name and movies not found, then return empty list`() = runTest {
        // Given
        val query = "Eg"
        coEvery { searchRepository.getMoviesByCountry(query, 1) } returns emptyList()

        // When
        val result = searchByCountryUseCase(query, 1)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `When search by valid country name, then return list of movies relate to country`() = runTest {
        // Given
        val query = "Eg"
        coEvery { searchRepository.getMoviesByCountry(query, 1) } returns getMoviesByCountry()

        // When
        val result = searchByCountryUseCase.invoke(query, 1)

        // Then
        assertThat(result).containsExactlyElementsIn(getMoviesByCountry())
    }

    @Test
    fun `When search by invalid country name, then return list of movies relate to country`() = runTest {
        // Given
        val query = "abcd"
        val page = 1
        coEvery { searchRepository.getMoviesByCountry(query, page) } returns emptyList()

        // When
        val result = searchByCountryUseCase.invoke(query, page)

        // Then
        assertThat(result).isEmpty()
    }

    private fun getMoviesByCountry(): List<Movie> {
        return (0..5).map {
            Movie(it.toLong(), "Filmy", 5.0, LocalDate(2024, 6, 23), emptyList(), "path")
        }
    }
}