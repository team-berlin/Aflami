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
import repository.TvShowDetailsRepository

class GetSimilarSeriesUseCaseTest {

    private val seriesDetailsRepository = mockk<TvShowDetailsRepository>()
    private lateinit var getSimilarSeriesUseCase: GetSimilarSeriesUseCase

    @Before
    fun setUp() {
        getSimilarSeriesUseCase = GetSimilarSeriesUseCase(seriesDetailsRepository)
    }

    @Test
    fun `should return media similar to media that returned when repository is called`() = runTest {
        // given
        val mediaId = 0L
        coEvery {
            seriesDetailsRepository.getSeriesSimilar(
                mediaId,
            )
        } returns getSimilarSeries()

        //when
        val result = getSimilarSeriesUseCase.invoke(mediaId)
        val expected=getSimilarSeries()

        // then
        Truth.assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesDetailsRepository.getSeriesSimilar(mediaId) }
    }

    @Test
    fun `should return empty list when media is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery {
            seriesDetailsRepository.getSeriesSimilar(
                mediaId,
            )
        } returns emptyList()

        //when
        val result = getSimilarSeriesUseCase.invoke(mediaId)

        //then
        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesDetailsRepository.getSeriesSimilar(mediaId) }

    }

    @Test
    fun `should throw exception if seriesDetailsRepository throw exception `()= runTest {
        //give
        val mediaId = 3L
        val exception=Exception()
        coEvery { seriesDetailsRepository.getSeriesSimilar(
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
                    id = 1L,
                    title = "Inception$i",
                    rating = 8.8,
                    releaseYear = LocalDate(2010, 7, 16),
                    genre = listOf(28, 12, 878),
                    poster = "https://poster$i",
                    backdropPath = "https://backdrop$i",
                    overview = "A thief who steals corporate secrets through the use of dream-sharing technology...",
                    releaseDate = "2010-07-16",
                    runtime = 148
                )
            )
        }
        return seriesList
    }


}