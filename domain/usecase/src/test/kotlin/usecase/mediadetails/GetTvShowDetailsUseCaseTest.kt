package usecase.mediadetails

import com.berlin.entity.GenreEntity
import com.berlin.entity.ProductionCompanyEntity
import com.berlin.entity.SeasonEntity
import com.berlin.entity.TvShowDetails
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.TvShowDetailsRepository

class GetTvShowDetailsUseCaseTest {

    private val repository = mockk<TvShowDetailsRepository>()
    private lateinit var getTvShowDetailsUseCase: GetTvShowDetailsUseCase

    @Before
    fun setUp() {
        getTvShowDetailsUseCase = GetTvShowDetailsUseCase(repository)
    }

    @Test
    fun `should return tv show details when repository returns data`() = runTest {
        val tvShowId = 1L
        val language = "en-US"
        val expectedDetails = getFakeTvShowDetails()

        coEvery { repository.getTvShowDetails(tvShowId, language) } returns expectedDetails

        val result = getTvShowDetailsUseCase(tvShowId, language)

        assertThat(result).isEqualTo(expectedDetails)
        coVerify(exactly = 1) { repository.getTvShowDetails(tvShowId, language) }
    }

    @Test
    fun `should return null when repository returns null`() = runTest {
        val tvShowId = 2L
        val language = "en-US"

        coEvery { repository.getTvShowDetails(tvShowId, language) } returns null

        val result = getTvShowDetailsUseCase(tvShowId, language)

        assertThat(result).isNull()
        coVerify(exactly = 1) { repository.getTvShowDetails(tvShowId, language) }
    }

    private fun getFakeTvShowDetails(): TvShowDetails {
        return TvShowDetails(
            id = 1L,
            title = "Breaking Bad",
            overview = "A high school chemistry teacher turns to a life of crime after a cancer diagnosis.",
            posterUrl = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            releaseDate = "2008-01-20",
            rating = 9.5,
            runtime = 47,
            genres = listOf(
                GenreEntity(id = 18, name = "Drama"),
                GenreEntity(id = 80, name = "Crime")
            ),
            productionCompanies = listOf(
                ProductionCompanyEntity(
                    id = 1,
                    name = "Sony Pictures Television",
                    poster = "https://image.tmdb.org/t/p/w500/company_logo.jpg",
                    originCountry = "US"
                )
            ),
            seasons = listOf(
                SeasonEntity(
                    id = 1,
                    seasonNumber = 1,
                    name = "Season 1",
                    overview = "First season introduction...",
                    posterUrl = "https://image.tmdb.org/t/p/w500/season1.jpg",
                    episodeCount = 7,
                    airDate = "2008-01-20",
                    voteAverage = 8.5
                )
            ),
            originCountry = "US",
            numberOfSeasons = 5
        )
    }

}
