package usecase.mediadetails


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
import usecase.tvshow.GetTVShowDetailsUseCase

class GetTvShowDetailsUseCaseTest {

    private val repository = mockk<TVShowDetailsRepository>()
    private lateinit var getTvShowDetailsUseCase: GetTVShowDetailsUseCase

    @Before
    fun setUp() {
        getTvShowDetailsUseCase = GetTVShowDetailsUseCase(repository)
    }

    @Test
    fun `should return tv show details when repository returns data`() = runTest {
        val tvShowId = 1L
        val language = "en-US"
        val expectedDetails = getFakeTvShowDetails()

        coEvery { repository.getTVShowDetails(tvShowId) } returns expectedDetails

        val result = getTvShowDetailsUseCase(tvShowId)

        assertThat(result).isEqualTo(expectedDetails)
        coVerify(exactly = 1) { repository.getTVShowDetails(tvShowId) }
    }

    @Test
    fun `should return null when repository returns null`() = runTest {
        val tvShowId = 2L
        val language = "en-US"

        coEvery { repository.getTVShowDetails(tvShowId) } returns null

        val result = getTvShowDetailsUseCase(tvShowId)

        assertThat(result).isNull()
        coVerify(exactly = 1) { repository.getTVShowDetails(tvShowId) }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Given
        val movieId = 3L
        val language = "en-US"
        val exception = RuntimeException()
        coEvery { repository.getTVShowDetails(movieId) } throws exception

        // When + Then
        assertThrows<RuntimeException>{
            getTvShowDetailsUseCase(movieId)
        }
    }


    private fun getFakeTvShowDetails(): TVShow {
        return TVShow(
            id = TODO(),
            title = TODO(),
            rating = TODO(),
            posterURL = TODO(),
            releaseDate = TODO(),
            screenShot = TODO(),
            description = TODO(),
            genres = TODO(),
            duration = TODO(),
            hasVideo = TODO(),
            productionCompanies = TODO(),
            originCountry = TODO(),
            seasons = TODO(),
            galleryUrl = TODO(),
            reviews = TODO()
//            id = 1L,
//            title = "Breaking Bad",
//            overview = "A high school chemistry teacher turns to a life of crime after a cancer diagnosis.",
//            posterUrl = "https://image.tmdb.org/t/p/w500/poster.jpg",
//            backdropUrl = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
//            releaseDate = "2008-01-20",
//            rating = 9.5,
//            runtime = 47,
//            genres = listOf(
//                GenreEntity(id = 18, name = "Drama"),
//                GenreEntity(id = 80, name = "Crime")
//            ),
//            productionCompanies = listOf(
//                ProductionCompany(
//                    id = 1,
//                    name = "Sony Pictures Television",
//                    poster = "https://image.tmdb.org/t/p/w500/company_logo.jpg",
//                    originCountry = "US"
//                )
//            ),
//            seasons = listOf(
//                Season(
//                    id = 1,
//                    seasonNumber = 1,
//                    name = "Season 1",
//                    description = "First season introduction...",
//                    poster = "https://image.tmdb.org/t/p/w500/season1.jpg",
//                    episodeCount = 7,
//                    airDate = "2008-01-20",
//                    rating = 8.5
//                )
//            ),
//            originCountry = "US",
//            numberOfSeasons = 5
        )
    }
}
