package usecase.mediadetails

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository

class GetMovieGalleryUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private lateinit var getMovieGalleryUseCase: GetMovieGalleryUseCase

    @Before
    fun setUp() {
        getMovieGalleryUseCase = GetMovieGalleryUseCase(movieDetailsRepository)
    }

    @Test
    fun `should return list of strings when calling repository`() = runTest {
        val movieID: Long = 505
        val posters = listOf("https", "https")
        coEvery { movieDetailsRepository.getMovieImages(movieID) } returns posters

        val callResult = getMovieGalleryUseCase(movieID)

        assertThat(callResult).isEqualTo(posters)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieImages(movieID) }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        val exception = RuntimeException()
        val movieID: Long = 505
        coEvery { movieDetailsRepository.getMovieImages(505) } throws exception

        assertThrows<RuntimeException> {
            getMovieGalleryUseCase(movieID)
        }
    }
}