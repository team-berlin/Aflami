package usecase.mediadetails

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TvShowDetailsRepository

class GetSeriesGalleryUseCaseTest {
    private val seriesDetailsRepository: TvShowDetailsRepository = mockk()
    private lateinit var getSeriesGalleryUseCase: GetSeriesGalleryUseCase

    @Before
    fun setUp() {
        getSeriesGalleryUseCase = GetSeriesGalleryUseCase(seriesDetailsRepository)
    }

    @Test
    fun `should return list of strings when calling repository`() = runTest {
        val movieID: Long = 505
        val posters = listOf("https", "https")
        coEvery { seriesDetailsRepository.getSeriesImages(movieID) } returns posters

        val callResult = getSeriesGalleryUseCase(movieID)

        assertThat(callResult).isEqualTo(posters)
        coVerify(exactly = 1) { seriesDetailsRepository.getSeriesImages(movieID) }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        val exception = RuntimeException()
        val movieID: Long = 505
        coEvery { seriesDetailsRepository.getSeriesImages(505) } throws exception

        assertThrows<RuntimeException> {
            getSeriesGalleryUseCase(movieID)
        }
    }

}