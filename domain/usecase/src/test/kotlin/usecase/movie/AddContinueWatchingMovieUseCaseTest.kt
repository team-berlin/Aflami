package usecase.movie

import com.berlin.entity.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class AddContinueWatchingMovieUseCaseTest {
    private val repository: MovieRepository = mockk(relaxed = true)
    private lateinit var addContinueWatchingMovieUseCase: AddContinueWatchingMovieUseCase

    @Before
    fun setup() {
        addContinueWatchingMovieUseCase = AddContinueWatchingMovieUseCase(repository)
    }

    @Test
    fun `should call addContinueWatchingMovie once with correct movie`() = runTest {
        addContinueWatchingMovieUseCase(TEST_MOVIE)

        coVerify(exactly = 1) {
            repository.addContinueWatchingMovie(TEST_MOVIE)
        }
    }

    @Test
    fun `should throw exception when repository fails`() = runTest {
        coEvery { repository.addContinueWatchingMovie(TEST_MOVIE) } throws Exception(DB_ERROR)

        assertThrows<Exception>{
            addContinueWatchingMovieUseCase(TEST_MOVIE)
        }

        coVerify(exactly = 1) {
            repository.addContinueWatchingMovie(TEST_MOVIE)
        }
    }

    companion object {
         val TEST_MOVIE = Movie(
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
            productionCompanies = emptyList(),
            originCountry = "PS",
            galleryUrl = emptyList()
        )
        const val DB_ERROR = "DB error"
    }
}