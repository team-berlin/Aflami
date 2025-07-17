package usecase

import com.berlin.entity.MediaCast
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.TvShowDetailsRepository

class GetSeriesCastUseCaseTest {

    private val seriesDetailsRepository = mockk<TvShowDetailsRepository>()
    private lateinit var getSeriesCastUseCase: GetSeriesCastUseCase

    @Before
    fun setUp() {
        getSeriesCastUseCase = GetSeriesCastUseCase(seriesDetailsRepository)
    }

    @Test
    fun `should return media cast related to media id when invoke is called`() = runTest {
        // given
        val mediaId = 0L
        val language = "en-US"
        coEvery {
            seriesDetailsRepository.getSeriesCastDetails(
                mediaId,
                language
            )
        } returns getSeriesCast()

        //when
        val result = getSeriesCastUseCase.invoke(mediaId, language)

        // then
        assertThat(result).isEqualTo(getSeriesCast())
    }

    @Test
    fun `should return empty list when media cast is not found`() = runTest {
        //given
        val seriesId = 2L
        val language = "en-US"
        coEvery {
            seriesDetailsRepository.getSeriesCastDetails(
                seriesId,
                language
            )
        } returns emptyList()

        //when
        val result = getSeriesCastUseCase.invoke(seriesId, language)

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