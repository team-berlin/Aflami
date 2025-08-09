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

    private val tvShowRepository: TVShowRepository = mockk()
    private val addContinueWatchingTVShowUseCase: AddContinueWatchingTVShowUseCase =
        AddContinueWatchingTVShowUseCase(tvShowRepository)

    @Test
    fun `should call addContinueWatchingMovie once with correct movie`() = runTest {
        // Arrange
        coEvery { tvShowRepository.addContinueWatchingTVShow(TV_SHOW) } returns Unit

        // Act
        addContinueWatchingTVShowUseCase(TV_SHOW)

        // Assert
        coVerify(exactly = 1) {
            tvShowRepository.addContinueWatchingTVShow(TV_SHOW)
        }
    }

    @Test
    fun `should throw exception when repository fails`() = runTest {
        // Arrange
        coEvery { tvShowRepository.addContinueWatchingTVShow(TV_SHOW) } throws
                Exception()

        // Act
        assertThrows<Exception> { addContinueWatchingTVShowUseCase(TV_SHOW) }

        // Assert
        coVerify(exactly = 1) {
            tvShowRepository.addContinueWatchingTVShow(TV_SHOW)
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
            companyProductions = emptyList(),
            originCountry = "PS",
            galleryUrl = emptyList(),
            numberOfSeasons = 2,
        )
    }
}