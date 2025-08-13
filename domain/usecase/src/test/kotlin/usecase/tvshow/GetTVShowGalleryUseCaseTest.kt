package usecase.tvshow

import com.berlin.entity.MediaImage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetTVShowGalleryUseCaseTest {

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private val getTVShowGalleryUseCase: GetTVShowGalleryUseCase =
        GetTVShowGalleryUseCase(tvShowDetailsRepository)

    @Test
    fun `should return list of gallery image URLs when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsImages(TV_SHOW_ID) } returns POSTER

        // Act
        val result = getTVShowGalleryUseCase(TV_SHOW_ID)

        // Assert
        assertThat(result).isEqualTo(POSTER)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsImages(TV_SHOW_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsImages(TV_SHOW_ID) } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getTVShowGalleryUseCase(TV_SHOW_ID) }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsImages(TV_SHOW_ID) }
    }

    companion object {
        const val EXCEPTION = "Error to get tv show gallery"
        const val TV_SHOW_ID = 321L
        val POSTER = MediaImage(
            backdrops = listOf(
                "https://image.tmdb.org/t/p/w500/backdrop1.jpg",
                "https://image.tmdb.org/t/p/w500/backdrop2.jpg",
                "https://image.tmdb.org/t/p/w500/backdrop3.jpg"
            ),
            posters = listOf(
                "https://image.tmdb.org/t/p/w500/poster1.jpg",
                "https://image.tmdb.org/t/p/w500/poster2.jpg",
                "https://image.tmdb.org/t/p/w500/poster3.jpg"
            )
        )
    }
}