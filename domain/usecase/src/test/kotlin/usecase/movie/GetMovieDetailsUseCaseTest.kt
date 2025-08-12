package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository
import usecase.movie.GetMovieCastUseCaseTest.Companion.actors

class GetMovieDetailsUseCaseTest {

    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(movieDetailsRepository)


    @Test
    fun `should return movie details when repository returns data`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieDetails(movie.id) } returns movie

        //Act
        val result = getMovieDetailsUseCase(movie.id)

        // Assert
        assertThat(result).isEqualTo(movie)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieDetails(movie.id) }
    }
    
    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieDetails(movie.id) } throws Exception()

        //Act
        assertThrows<Exception> {
            getMovieDetailsUseCase(movie.id)
        }

        // Assert
        coVerify(exactly = 1) {
            movieDetailsRepository.getMovieDetails(movie.id)
        }
    }

    companion object {
        val movie = Movie(
            id = 90L,
            title = "Test Movie",
            rating = 7.9,
            releaseDate = "1/12/2001",
            posterURL = "/test.jpg",
            screenShot = "/test.jpg",
            description = "This is the test movie",
            genres = emptyList(),
            duration = 3,
            hasVideo = false,
            companyProductions = emptyList(),
            originCountry = "PS",
            galleryUrl = emptyList(),
            reviews = emptyList(),
            isFavourite = false,
        )
    }
}