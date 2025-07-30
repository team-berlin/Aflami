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
import repository.TVShowRepository

class GetSearchTVShowsUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk()
    private lateinit var getSearchTVShowsUseCase: GetSearchTVShowsUseCase

    @Before
    fun setUp() {
        getSearchTVShowsUseCase = GetSearchTVShowsUseCase(tvShowRepository)
    }

    @Test
    fun `should return list of TV shows when calling repository`() = runTest {
        coEvery { tvShowRepository.searchTVShow(QUERY, PAGE) } returns TV_SHOWS

        val callResult = getSearchTVShowsUseCase(QUERY, PAGE)

        assertThat(callResult).isEqualTo(TV_SHOWS)
        coVerify(exactly = 1) { tvShowRepository.searchTVShow(QUERY, PAGE) }
    }

    @Test
    fun `should throw exception if tvShowRepository throws exception`() = runTest {
        coEvery { tvShowRepository.searchTVShow(QUERY, PAGE) } throws Exception()

        assertThrows<Exception> {
            getSearchTVShowsUseCase(QUERY, PAGE)
        }
    }

    companion object {
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