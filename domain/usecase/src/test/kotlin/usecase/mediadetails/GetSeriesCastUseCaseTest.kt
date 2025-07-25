package usecase.mediadetails

import com.berlin.entity.MediaCast
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertThrows
import repository.TvShowDetailsRepository

class GetSeriesCastUseCaseTest {

    private val seriesDetailsRepository = mockk<TvShowDetailsRepository>()
    private lateinit var getSeriesCastUseCase: GetSeriesCastUseCase

    @Before
    fun setUp() {
        getSeriesCastUseCase = GetSeriesCastUseCase(seriesDetailsRepository)
    }

    @Test
    fun `should return media cast related to media id when repository is called`() = runTest {
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
        val expected=getSeriesCast()

        // then
        assertThat(result).isEqualTo(expected)
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

    @Test
    fun `should throw exception if movieDetailsRepository throw exception `()= runTest {
        //give
        val mediaId = 3L
        val language = "en-US"
        val exception=Exception()
        coEvery { seriesDetailsRepository.getSeriesCastDetails(mediaId, language) } throws exception
        //when & then
        assertThrows<Exception> {
            getSeriesCastUseCase.invoke(mediaId, language)
        }
    }

    private fun getSeriesCast(): List<MediaCast> {

        val castList = mutableListOf<MediaCast>()
        for (i in 0..5) {
            castList.add(
                MediaCast(
                    mediaId = i.toLong(),
                    name = "name $i",
                    poster = "poster $i"
                )
            )
        }
        return castList
    }
}