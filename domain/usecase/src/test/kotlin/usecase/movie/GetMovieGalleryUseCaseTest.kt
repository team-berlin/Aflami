package usecase.movie

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
        coEvery { movieDetailsRepository.getMovieGallery(MOVIE_ID) } returns POSTER

        val callResult = getMovieGalleryUseCase(MOVIE_ID)

        assertThat(callResult).isEqualTo(POSTER)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieGallery(MOVIE_ID) }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        coEvery { movieDetailsRepository.getMovieGallery(MOVIE_ID) } throws Exception()

        assertThrows<Exception> {
            getMovieGalleryUseCase(MOVIE_ID)
        }
    }

    companion object{
        const val MOVIE_ID = 50L
        val POSTER = listOf("https", "https")
    }
}