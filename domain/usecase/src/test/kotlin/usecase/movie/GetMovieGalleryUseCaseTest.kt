package usecase.movie

import com.berlin.entity.MediaImage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository

class GetMovieGalleryUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase =
        GetMovieGalleryUseCase(movieDetailsRepository)


    @Test
    fun `should return list of strings when calling repository`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieImages(MOVIE_ID) } returns POSTER

        //Act
        val result = getMovieGalleryUseCase(MOVIE_ID)

        // Assert
        assertThat(result).isEqualTo(POSTER)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieImages(MOVIE_ID) }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieImages(MOVIE_ID) } throws Exception()

        // Act
        assertThrows<Exception> {
            getMovieGalleryUseCase(MOVIE_ID)
        }

        // Assert
        coVerify(exactly = 1) { movieDetailsRepository.getMovieImages(MOVIE_ID) }
    }

    companion object {
        const val MOVIE_ID = 50L
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