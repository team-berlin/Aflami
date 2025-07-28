package usecase.mediadetails

import com.berlin.entity.TVShow
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository
import usecase.tvshow.GetSimilarTVShowsUseCase

class GetSimilarSeriesUseCaseTest {

    private val seriesDetailsRepository = mockk<TVShowDetailsRepository>()
    private lateinit var getSimilarSeriesUseCase: GetSimilarTVShowsUseCase

    @Before
    fun setUp() {
        getSimilarSeriesUseCase = GetSimilarTVShowsUseCase(seriesDetailsRepository)
    }

    @Test
    fun `should return media similar to media that returned when repository is called`() = runTest {
        // given
        val mediaId = 0L
        coEvery {
            seriesDetailsRepository.getSimilarTVShows(
                mediaId,
            )
        } returns getSimilarSeries()

        //when
        val result = getSimilarSeriesUseCase.invoke(mediaId)
        val expected=getSimilarSeries()

        // then
        Truth.assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesDetailsRepository.getSimilarTVShows(mediaId) }
    }

    @Test
    fun `should return empty list when media is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery {
            seriesDetailsRepository.getSimilarTVShows(
                mediaId,
            )
        } returns emptyList()

        //when
        val result = getSimilarSeriesUseCase.invoke(mediaId)

        //then
        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesDetailsRepository.getSimilarTVShows(mediaId) }

    }

    @Test
    fun `should throw exception if seriesDetailsRepository throw exception `()= runTest {
        //give
        val mediaId = 3L
        val exception=Exception()
        coEvery { seriesDetailsRepository.getSimilarTVShows(
                mediaId,
            ) } throws exception
        //when & then
        assertThrows<Exception> {
            getSimilarSeriesUseCase.invoke(mediaId)
        }
    }

    private fun getSimilarSeries(): List<TVShow> {

        val seriesList = mutableListOf<TVShow>()
        for (i in 0..5) {
            seriesList.add(
                TVShow(
                    id = TODO(),
                    title = TODO(),
                    rating = TODO(),
                    posterURL = TODO(),
                    releaseDate = TODO(),
                    screenShot = TODO(),
                    description = TODO(),
                    genres = TODO(),
                    duration = TODO(),
                    hasVideo = TODO(),
                    productionCompanies = TODO(),
                    originCountry = TODO(),
                    seasons = TODO(),
                    galleryUrl = TODO(),
                    reviews = TODO()
//                    id = 1L,
//                    title = "Inception$i",
//                    rating = 8.8,
//                    releaseYear = LocalDate(2010, 7, 16),
//                    genre = listOf(28, 12, 878),
//                    poster = "https://poster$i",
//                    backdropPath = "https://backdrop$i",
//                    overview = "A thief who steals corporate secrets through the use of dream-sharing technology...",
//                    releaseDate = "2010-07-16",
//                    runtime = 148
                )
            )
        }
        return seriesList
    }


}