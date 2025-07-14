package usecase

import com.berlin.entity.MediaCast
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.SeriesDetailsRepository

class GetSeriesCastUseCaseTest {

    private val seriesDetailsRepository = mockk<SeriesDetailsRepository>()
    private lateinit var getSeriesCastUseCase: GetSeriesCastUseCase

    @Before
    fun setUp() {
        getSeriesCastUseCase = GetSeriesCastUseCase(seriesDetailsRepository)
    }

    @Test
    fun `should return media cast related to media id when invoke is called`() = runTest {
        // given
        val mediaId = 0L
        coEvery { seriesDetailsRepository.getSeriesCastDetails(mediaId) } returns getSeriesCast()

        //when
        val result = getSeriesCastUseCase.invoke(mediaId)

        // then
        assertThat(result).isEqualTo(getSeriesCast())
    }

    @Test
    fun `should return empty list when media cast is not found`() = runTest {
        //given
        val seriesId = 2L
        coEvery { seriesDetailsRepository.getSeriesCastDetails(seriesId) } returns emptyList()

        //when
        val result = getSeriesCastUseCase.invoke(seriesId)

        //then
        assertThat(result).isEmpty()

    }

    private fun getSeriesCast(): List<MediaCast> {

        return (0..5).map {
            MediaCast(
                it.toLong(),
                "name",
                "poster"
            )
        }
    }


}