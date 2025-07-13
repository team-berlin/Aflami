package usecase

import com.berlin.entity.TVShow
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import repository.SearchRepository

class GetSearchTvShowsUseCaseTest {
    private val searchRepository = mockk<SearchRepository>()
    private lateinit var getSearchTvShowsUseCase: GetSearchTvShowsUseCase

    @Before
    fun setUp() {
        getSearchTvShowsUseCase = GetSearchTvShowsUseCase(searchRepository)
    }

    @Test
    fun `When search TV shows with valid query and no results, then return empty list`() = runTest {
        // Given
        val query = "Breaking"
        val language = "en-US"
        coEvery { searchRepository.searchTVShow(query, language) } returns emptyList()

        // When
        val result = getSearchTvShowsUseCase.invoke(query, language)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `When search TV shows with valid query, then return list of TV shows`() = runTest {
        // Given
        val query = "Breaking"
        val language = "en-US"
        coEvery { searchRepository.searchTVShow(query, language) } returns getSampleTVShows()

        // When
        val result = getSearchTvShowsUseCase.invoke(query, language)

        // Then
        assertThat(result).isNotEmpty()
        assertThat(result.size).isEqualTo(5)
    }

    @Test
    fun `When search TV shows with random or invalid query, then return empty list`() = runTest {
        // Given
        val query = "abcdef"
        val language = "en-US"
        coEvery { searchRepository.searchTVShow(query, language) } returns emptyList()

        // When
        val result = getSearchTvShowsUseCase.invoke(query, language)

        // Then
        assertThat(result).isEmpty()
    }

    private fun getSampleTVShows(): List<TVShow> {
        return (1..5).map {
            TVShow(
                id = it.toLong(),
                title = "Show $it",
                rating = 7.5,
                releaseYear = LocalDate(2023, 1, it),
                genre = listOf(1, 2),
                poster = "/poster$it.jpg"
            )
        }
    }
}