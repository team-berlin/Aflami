package usecase

import com.berlin.entity.MediaCast
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.MovieDetailsRepository

class GetMovieCastUseCaseTest {

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
        val language = "en-US"
        coEvery {
            movieDetailsRepository.getMovieCastDetails(
                mediaId,
                language
            )
        } returns getMovieCast()

        //when
        val result = getMovieCastUseCase.invoke(mediaId, language)

        // then
        assertThat(result).isEqualTo(getMovieCast())
    }

    @Test
    fun `should return empty list when media cast is not found`() = runTest {
        //given
        val mediaId = 2L
        val language = "en-US"
        coEvery {
            movieDetailsRepository.getMovieCastDetails(
                mediaId,
                language
            )
        } returns emptyList()

        //when
        val result = getMovieCastUseCase.invoke(mediaId, language)

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