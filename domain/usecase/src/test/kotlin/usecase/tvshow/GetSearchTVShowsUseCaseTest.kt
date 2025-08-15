package usecase.tvshow

import com.berlin.entity.TVShow
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class GetSearchTVShowsUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk()
    private val getSearchTVShowsUseCase: GetSearchTVShowsUseCase =
        GetSearchTVShowsUseCase(tvShowRepository)

    @Test
    fun `should return list of TV shows when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowRepository.searchTVShow(QUERY, PAGE) } returns TV_SHOWS

        // Act
        val result = getSearchTVShowsUseCase(QUERY, PAGE)

        // Assert
        assertThat(result).isEqualTo(TV_SHOWS)
        coVerify(exactly = 1) { tvShowRepository.searchTVShow(QUERY, PAGE) }
    }

    @Test
    fun `should throw exception if tvShowRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.searchTVShow(QUERY, PAGE) } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getSearchTVShowsUseCase(QUERY, PAGE) }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowRepository.searchTVShow(QUERY, PAGE) }
    }

    companion object {
        const val EXCEPTION = "Error to get search"
        const val QUERY = "Stranger Things"
        const val PAGE = 1
        val TV_SHOWS = listOf(
            TVShow(
                id = 90L,
                title = "TV Show",
                rating = 7.9,
                posterURL = "/test.jpg",
                releaseDate = "1/12/2007",
                screenShot = "/test.jpg",
                description = "This is the test tv show",
                genres = emptyList(),
                duration = 3,
                hasVideo = false,
                companyProductions = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList(),
                numberOfSeasons = 2,
            ),
            TVShow(
                id = 90L,
                title = "TV Show",
                rating = 7.9,
                posterURL = "/test.jpg",
                releaseDate = "1/12/2007",
                screenShot = "/test.jpg",
                description = "This is the test tv show",
                genres = emptyList(),
                duration = 3,
                hasVideo = false,
                companyProductions = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList(),
                numberOfSeasons = 2,
            )
        )
    }
}