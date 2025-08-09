package usecase.tvshow

import com.berlin.entity.Video
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetTVShowVideosTest {
    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private val getTVShowVideos: GetTVShowVideos = GetTVShowVideos(tvShowDetailsRepository)

    @Test
    fun `should return first video when repository returns non-empty list`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowVideos(TV_SHOW_ID) } returns VIDEOS

        // Act
        getTVShowVideos(TV_SHOW_ID)

        // Assert
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowVideos(TV_SHOW_ID) }
    }

    @Test
    fun `should throw exception when repository fails`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowVideos(TV_SHOW_ID) } throws Exception()

        // Act
        assertThrows<Exception> { getTVShowVideos(TV_SHOW_ID) }

        // Assert
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowVideos(TV_SHOW_ID) }
    }

    companion object {
        const val TV_SHOW_ID = 123L
        val VIDEOS = listOf(
            Video(
                videoId = "vid1",
                videoUrl = "https://youtube.com/watch?v=abc123",
                site = "YouTube",
                videoType = "Trailer",
                videoLanguage = "en"
            ),
            Video(
                videoId = "vid2",
                videoUrl = "https://youtube.com/watch?v=xyz456",
                site = "YouTube",
                videoType = "Teaser",
                videoLanguage = "en"
            )
        )
    }
}