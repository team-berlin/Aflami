package usecase.mediadetails

import com.berlin.entity.Review
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository
import usecase.movie.GetMovieReviewUseCase

class GetMovieReviewUseCaseTest {

    private val movieDetailsRepository = mockk<MovieDetailsRepository>()
    private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase

    @Before
    fun setUp() {
        getMovieReviewUseCase = GetMovieReviewUseCase(movieDetailsRepository)
    }

    @Test
    fun `should return review related to media id when repository is called`() = runTest {
        val mediaId = 0L
        coEvery {
            movieDetailsRepository.getMovieReviews(
                mediaId,
            )
        } returns getMovieReview()

        //when
        val result = getMovieReviewUseCase.invoke(mediaId)
        val expected = getMovieReview()
        // then
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieReviews(mediaId) }
    }

    @Test
    fun `should return empty list when review is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery { movieDetailsRepository.getMovieReviews(mediaId) } returns emptyList()

        //when
        val result = getMovieReviewUseCase.invoke(mediaId)

        //then
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieDetailsRepository.getMovieReviews(mediaId) }

    }

    @Test
    fun `should throw exception if movieDetailsRepository throw exception `() = runTest {
        //give
        val mediaId = 3L
        val exception = Exception()
        coEvery { movieDetailsRepository.getMovieReviews(mediaId) } throws exception

        //when & then
        assertThrows<Exception> {
            getMovieReviewUseCase.invoke(mediaId)
        }
    }


    private fun getMovieReview(): List<Review> {

        val movieList = mutableListOf<Review>()
        for (i in 0..5) {
            movieList.add(
                Review(
                    id = i.toString(),
                    name = "Manuel São Bento$i",
                    userName = "name$i",
                    avatarImage = "https//:$i",
                    rating = i.toDouble(),
                    content = "Hmmm! I wasn’t sure if I was watching a sentimental edition of “Hawaii Five-O” here or a collection of outtakes from a “Sonic” movie as this rather disappointingly trundles along for the guts of two hours. It’s starts Read more",
                    date = "20/$i/2000"
                )
            )
        }
        return movieList
    }
}