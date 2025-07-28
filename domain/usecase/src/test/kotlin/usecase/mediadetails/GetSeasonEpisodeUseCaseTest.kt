package usecase.mediadetails

import com.berlin.entity.Episode
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetSeasonEpisodeUseCaseTest {

    private val tvShowDetailsRepository = mockk<TVShowDetailsRepository>()
    private lateinit var getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase

    @Before
    fun setUp() {
        getSeasonEpisodesUseCase = GetSeasonEpisodesUseCase(tvShowDetailsRepository)
    }

    @Test
    fun `should return episodes when repository returns data`() = runTest {
        val seriesId = 1L
        val seasonNumber = 1
        val expectedEpisodes = getFakeEpisodes()

        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(
                seriesId,
                seasonNumber
            )
        } returns expectedEpisodes

        val result = getSeasonEpisodesUseCase(seriesId, seasonNumber)

        assertThat(result).isEqualTo(expectedEpisodes)
        coVerify(exactly = 1) { tvShowDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber) }
    }

    @Test
    fun `should return empty list when no episodes found`() = runTest {
        val seriesId = 2L
        val seasonNumber = 3

        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(
                seriesId,
                seasonNumber
            )
        } returns emptyList()

        val result = getSeasonEpisodesUseCase(seriesId, seasonNumber)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { tvShowDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber) }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Given
        val seriesId = 10L
        val seasonNumber = 4
        val exception = RuntimeException()

        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber)
        } throws exception

        // When + Then
        assertThrows<RuntimeException> {
            getSeasonEpisodesUseCase(seriesId, seasonNumber)
        }
    }


    private fun getFakeEpisodes(): List<Episode> {
        return (1..5).map { i ->
            Episode(
                episodeId = i,
                episodeNumber = i,
                name = "Episode $i",
                description = "Overview of episode $i",
                rating = 8.0 + i,
                stillPath = "https://image.tmdb.org/t/p/w500/still_$i.jpg",
                airDate = "2020-0$i-01"
            )
        }
    }
}
