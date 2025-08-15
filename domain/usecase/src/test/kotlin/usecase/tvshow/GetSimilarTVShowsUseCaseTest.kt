package usecase.tvshow

import com.berlin.entity.TVShow
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetSimilarTVShowsUseCaseTest {
    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private val getSimilarTVShowsUseCase: GetSimilarTVShowsUseCase =
        GetSimilarTVShowsUseCase(tvShowDetailsRepository)


    @Test
    fun `should return list of TV shows when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsSimilar(TV_SHOW_ID) } returns TV_SHOWS

        // Act
        val result = getSimilarTVShowsUseCase(TV_SHOW_ID)

        // Assert
        assertThat(result).isEqualTo(TV_SHOWS)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsSimilar(TV_SHOW_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsSimilar(TV_SHOW_ID) } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> {
            getSimilarTVShowsUseCase(TV_SHOW_ID)
        }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsSimilar(TV_SHOW_ID) }
    }

    companion object {
        const val EXCEPTION = "Error to get similar tv show"
        const val TV_SHOW_ID = 101L
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