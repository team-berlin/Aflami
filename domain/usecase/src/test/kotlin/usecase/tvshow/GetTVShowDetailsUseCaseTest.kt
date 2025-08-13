package usecase.tvshow

import com.berlin.entity.TVShow

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetTVShowDetailsUseCaseTest {

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private val getTVShowDetailsUseCase: GetTVShowDetailsUseCase =
        GetTVShowDetailsUseCase(tvShowDetailsRepository)

    @Test
    fun `should return TV show details when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowDetails(TV_SHOW_ID) } returns TV_SHOW_DETAILS

        // Act
        val result = getTVShowDetailsUseCase(TV_SHOW_ID)

        // Assert
        assertThat(result).isEqualTo(TV_SHOW_DETAILS)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowDetails(TV_SHOW_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowDetails(TV_SHOW_ID) } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getTVShowDetailsUseCase(TV_SHOW_ID) }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowDetails(TV_SHOW_ID) }
    }

    companion object {
        const val EXCEPTION = "Error to get tv show details"
        const val TV_SHOW_ID = 123L
        val TV_SHOW_DETAILS = TVShow(
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
    }
}