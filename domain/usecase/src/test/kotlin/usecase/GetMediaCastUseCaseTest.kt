package usecase

import com.berlin.entity.MediaCast
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import repository.MovieDetailsRepository

class GetMediaCastUseCaseTest {

    private val movieDetailsRepository = mockk<MovieDetailsRepository>()
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase

    @Before
    fun setUp() {
        getMovieCastUseCase = GetMovieCastUseCase(movieDetailsRepository)
    }

    @Test
    fun `should return media cast related to media id when invoke is called`() = runTest {
        // given
        val mediaId = 0L
        coEvery { movieDetailsRepository.getMovieCastDetails(mediaId) } returns getMovieCast()

        //when
        val result = getMovieCastUseCase.invoke(mediaId)

        // then
        assertThat(result).isEqualTo(getMovieCast())
    }

    @Test
    fun `should return empty list when media cast is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery { movieDetailsRepository.getMovieCastDetails(mediaId) } returns emptyList()

        //when
        val result = getMovieCastUseCase.invoke(mediaId)

        //then
        assertThat(result).isEmpty()

    }

    private fun getMovieCast(): List<MediaCast> {

        return (0..5).map {
            MediaCast(
                it.toLong(),
                "name",
                "poster"
            )
        }
    }


}