package usecase.mediadetails

import com.berlin.entity.MediaCast
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository

class GetMovieCastUseCaseTest {

    private val movieDetailsRepository = mockk<MovieDetailsRepository>()
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase

    @Before
    fun setUp() {
        getMovieCastUseCase = GetMovieCastUseCase(movieDetailsRepository)
    }

    @Test
    fun `should return media cast related to media id when repository is called`() = runTest {
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
        coVerify(exactly = 1) { movieDetailsRepository.getMovieCastDetails(mediaId, language) }
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
        coVerify(exactly = 1) { movieDetailsRepository.getMovieCastDetails(mediaId, language) }

    }

    @Test
    fun `should throw exception if movieDetailsRepository throw exception `()= runTest {
        //give
        val mediaId = 3L
        val language = "en-US"
        val exception=Exception()
        coEvery { movieDetailsRepository.getMovieCastDetails(mediaId, language) } throws exception
        //when & then
        assertThrows<Exception> {
            getMovieCastUseCase.invoke(mediaId, language)
        }
    }

    private fun getMovieCast(): List<MediaCast> {

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