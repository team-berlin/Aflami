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
    private lateinit var getTVShowDetailsUseCase: GetTVShowDetailsUseCase

    @Before
    fun setUp() {
        getTVShowDetailsUseCase = GetTVShowDetailsUseCase(tvShowDetailsRepository)
    }

    @Test
    fun `should return TV show details when calling repository`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowDetails(TV_SHOW_ID) } returns TV_SHOW_DETAILS

        val callResult = getTVShowDetailsUseCase(TV_SHOW_ID)

        assertThat(callResult).isEqualTo(TV_SHOW_DETAILS)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowDetails(TV_SHOW_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowDetails(TV_SHOW_ID) } throws Exception()

        assertThrows<Exception> {
            getTVShowDetailsUseCase(TV_SHOW_ID)
        }
    }

    companion object {
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
            productionCompanies = emptyList(),
            originCountry = "PS",
            seasons = emptyList(),
            galleryUrl = emptyList(),
            reviews = emptyList()
        )
    }
}