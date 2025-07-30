package usecase.tvshow

import com.berlin.entity.TVShow
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository
import usecase.movie.AddContinueWatchingMovieUseCaseTest.Companion.DB_ERROR

class AddContinueWatchingTVShowUseCaseTest {

    private val repository: TVShowRepository = mockk(relaxed = true)
    private lateinit var addContinueWatchingTVShowUseCase: AddContinueWatchingTVShowUseCase

    @Before
    fun setup() {
        addContinueWatchingTVShowUseCase = AddContinueWatchingTVShowUseCase(repository)
    }

    @Test
    fun `should call addContinueWatchingMovie once with correct movie`() = runTest {
        addContinueWatchingTVShowUseCase(TV_SHOW)

        coVerify(exactly = 1) {
            repository.addContinueWatchingTVShow(TV_SHOW)
        }
    }

    @Test
    fun `should throw exception when repository fails`() = runTest {
        coEvery { repository.addContinueWatchingTVShow(TV_SHOW) } throws Exception(DB_ERROR)

        assertThrows<Exception> {
            addContinueWatchingTVShowUseCase(TV_SHOW)
        }

        coVerify(exactly = 1) {
            repository.addContinueWatchingTVShow(TV_SHOW)
        }
    }

    companion object {
        val TV_SHOW = TVShow(
            id = 90L,
            title = "TV Show",
            rating = 7.9,
            posterURL = "/test.jpg",
            releaseDate = "1/12/2007",
            screenShot = "/test.jpg",
            description = "This is the test tv show",
            genres = emptyList(),
            duration = 3,
            hasVideo = false,
            productionCompanies = emptyList(),
            originCountry = "PS",
            seasons = emptyList(),
            galleryUrl = emptyList(),
            reviews = emptyList()
        )
    }
}