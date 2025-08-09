package usecase.tvshow

import com.berlin.entity.TVShow
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class GetTopRatedTVShowUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk()
    private val getTopRatedTVShowUseCase: GetTopRatedTVShowUseCase =
        GetTopRatedTVShowUseCase(tvShowRepository)


    @Test
    fun `should return list of TV shows when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowRepository.getTopRatedTVShows(PAGE) } returns TV_SHOWS

        // Act
        getTopRatedTVShowUseCase(PAGE)

        // Assert
        coVerify(exactly = 1) { tvShowRepository.getTopRatedTVShows(PAGE) }
    }

    @Test
    fun `should throw exception if tvShowRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.getTopRatedTVShows(PAGE) } throws Exception()

        // Act
        assertThrows<Exception> {
            getTopRatedTVShowUseCase(PAGE)
        }

        // Assert
        coVerify(exactly = 1) { tvShowRepository.getTopRatedTVShows(PAGE) }
    }

    companion object {
        const val PAGE = 1
        val TV_SHOWS = listOf(
            TVShow(
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
            ),
            TVShow(
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
        )
    }
}