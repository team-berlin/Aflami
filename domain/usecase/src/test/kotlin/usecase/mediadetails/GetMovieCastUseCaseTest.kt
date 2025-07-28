package usecase.mediadetails

import com.berlin.entity.Actor
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository
import usecase.movie.GetMovieCastUseCase

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
        coEvery {
            movieDetailsRepository.getMovieActors(
                mediaId,
            )
        } returns getMovieCast()

        //when
        val result = getMovieCastUseCase.invoke(mediaId)

        // then
        assertThat(result).isEqualTo(getMovieCast())
        coVerify(exactly = 1) { movieDetailsRepository.getMovieActors(mediaId) }
    }

    @Test
    fun `should return empty list when media cast is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery {
            movieDetailsRepository.getMovieActors(
                mediaId,
            )
        } returns emptyList()

        //when
        val result = getMovieCastUseCase.invoke(mediaId)

        //then
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieDetailsRepository.getMovieActors(mediaId) }

    }

    @Test
    fun `should throw exception if movieDetailsRepository throw exception`() = runTest {
        //give
        val mediaId = 3L
        val exception=Exception()
        coEvery { movieDetailsRepository.getMovieActors(mediaId) } throws exception
        //when & then
        assertThrows<Exception> {
            getMovieCastUseCase.invoke(mediaId)
        }
    }

    private fun getMovieCast(): List<Actor> {

        val castList = mutableListOf<Actor>()
        for (i in 0..5) {
            castList.add(
                Actor(
                    id = i.toLong(),
                    name = "name $i",
                    posterURL = "poster $i"
                )
            )
        }
        return castList
    }
}