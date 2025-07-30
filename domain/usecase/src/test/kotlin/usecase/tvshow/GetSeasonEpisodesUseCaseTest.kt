package usecase.tvshow

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

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private lateinit var getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase

    @Before
    fun setUp() {
        getSeasonEpisodesUseCase = GetSeasonEpisodesUseCase(tvShowDetailsRepository)
    }

    @Test
    fun `should return episodes when repository returns data`() = runTest {

        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(
                TV_SHOW_ID, TV_SHOW_NUMBER
            )
        } returns getFakeEpisodes()

        val result = getSeasonEpisodesUseCase(TV_SHOW_ID, TV_SHOW_NUMBER)

        assertThat(result).isEqualTo(getFakeEpisodes())
        coVerify(exactly = 1) {
            tvShowDetailsRepository.getSeasonEpisodes(
                TV_SHOW_ID,
                TV_SHOW_NUMBER
            )
        }
    }

    @Test
    fun `should return empty list when no episodes found`() = runTest {
        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(
                TV_SHOW_ID, TV_SHOW_NUMBER
            )
        } returns emptyList()

        val result = getSeasonEpisodesUseCase(TV_SHOW_ID, TV_SHOW_NUMBER)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) {
            tvShowDetailsRepository.getSeasonEpisodes(
                TV_SHOW_ID,
                TV_SHOW_NUMBER
            )
        }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(TV_SHOW_ID, TV_SHOW_NUMBER)
        } throws Exception()

        assertThrows<Exception> {
            getSeasonEpisodesUseCase(TV_SHOW_ID, TV_SHOW_NUMBER)
        }
    }

    companion object {
        const val TV_SHOW_ID = 1L
        const val TV_SHOW_NUMBER = 4
    }


    private fun getFakeEpisodes(): List<Episode> {
        return (1..5).map { i ->
            Episode(
                airDate = "2020-0$i-01",
                episodeNumber = i,
                episodeType = "$i",
                episodeId = i.toLong(),
                name = "Episode $i",
                description = "Overview of episode $i",
                duration = i,
                tvShowId = i,
                rating = i.toDouble()
            )
        }
    }
}