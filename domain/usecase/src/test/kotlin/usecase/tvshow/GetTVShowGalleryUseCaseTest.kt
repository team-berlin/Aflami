package usecase.tvshow

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetTVShowGalleryUseCaseTest {

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private lateinit var getTVShowGalleryUseCase: GetTVShowGalleryUseCase

    @Before
    fun setUp() {
        getTVShowGalleryUseCase = GetTVShowGalleryUseCase(tvShowDetailsRepository)
    }

    @Test
    fun `should return list of gallery image URLs when calling repository`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowGallery(TV_SHOW_ID) } returns GALLERY

        val callResult = getTVShowGalleryUseCase(TV_SHOW_ID)

        assertThat(callResult).isEqualTo(GALLERY)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowGallery(TV_SHOW_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowGallery(TV_SHOW_ID) } throws Exception()

        assertThrows<Exception> {
            getTVShowGalleryUseCase(TV_SHOW_ID)
        }
    }

    companion object {
        const val TV_SHOW_ID = 321L
        val GALLERY = listOf(
            "https:/gallery1.jpg",
            "https:/gallery2.jpg"
        )
    }
}