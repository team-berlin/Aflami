package usecase.tvshow

import com.berlin.entity.Episode
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetSeasonEpisodeUseCaseTest {

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase =
        GetSeasonEpisodesUseCase(tvShowDetailsRepository)


    @Test
    fun `should return episodes when repository returns data`() = runTest {
        // Arrange
        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(
                TV_SHOW_ID, TV_SHOW_NUMBER
            )
        } returns EPISODE

        // Act
        val result = getSeasonEpisodesUseCase(TV_SHOW_ID, TV_SHOW_NUMBER)

        // Assert
        assertThat(result).isEqualTo(EPISODE)
        coVerify(exactly = 1) {
            tvShowDetailsRepository.getSeasonEpisodes(
                TV_SHOW_ID,
                TV_SHOW_NUMBER
            )
        }
    }

    @Test
    fun `should return empty list when no episodes found`() = runTest {
        // Arrange
        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(TV_SHOW_ID, TV_SHOW_NUMBER)
        } returns emptyList()

        // Act
        val result = getSeasonEpisodesUseCase(TV_SHOW_ID, TV_SHOW_NUMBER)

        // Assert
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
        // Arrange
        coEvery {
            tvShowDetailsRepository.getSeasonEpisodes(TV_SHOW_ID, TV_SHOW_NUMBER)
        } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> {
            getSeasonEpisodesUseCase(TV_SHOW_ID, TV_SHOW_NUMBER)
        }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) {
            tvShowDetailsRepository.getSeasonEpisodes(
                TV_SHOW_ID,
                TV_SHOW_NUMBER
            )
        }
    }

    companion object {
        const val EXCEPTION = "Error to get season"
        val EPISODE = listOf(
            Episode(
                airDate = "2020-0$2-01",
                episodeNumber = 2,
                episodeType = "",
                episodeId = 9L,
                name = "Episode ",
                description = "Overview of episode ",
                duration = 5,
                tvShowId = 26,
                stillPath = "",
                rating = 7.3
            )
        )
        const val TV_SHOW_ID = 1L
        const val TV_SHOW_NUMBER = 4
    }
}