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

class GetSimilarTVShowsUseCaseTest {
    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private lateinit var getSimilarTVShowsUseCase: GetSimilarTVShowsUseCase

    @Before
    fun setUp() {
        getSimilarTVShowsUseCase = GetSimilarTVShowsUseCase(tvShowDetailsRepository)
    }

    @Test
    fun `should return list of TV shows when calling repository`() = runTest {
        coEvery { tvShowDetailsRepository.getSimilarTVShows(TV_SHOW_ID) } returns TV_SHOWS

        val callResult = getSimilarTVShowsUseCase(TV_SHOW_ID)

        assertThat(callResult).isEqualTo(TV_SHOWS)
        coVerify(exactly = 1) { tvShowDetailsRepository.getSimilarTVShows(TV_SHOW_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        coEvery { tvShowDetailsRepository.getSimilarTVShows(TV_SHOW_ID) } throws Exception()

        assertThrows<Exception> {
            getSimilarTVShowsUseCase(TV_SHOW_ID)
        }
    }

    companion object {
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
                productionCompanies = emptyList(),
                originCountry = "PS",
                seasons = emptyList(),
                galleryUrl = emptyList(),
                reviews = emptyList()
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
                productionCompanies = emptyList(),
                originCountry = "PS",
                seasons = emptyList(),
                galleryUrl = emptyList(),
                reviews = emptyList()
            )
        )
    }
}