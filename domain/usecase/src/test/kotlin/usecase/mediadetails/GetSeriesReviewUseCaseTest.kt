package usecase.mediadetails

import com.berlin.entity.Review
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.TVShowDetailsRepository

class GetSeriesReviewUseCaseTest {


    private val seriesDetailsRepository = mockk<TVShowDetailsRepository>()
    private lateinit var getSeriesReviewUseCase: GetSeriesReviewUseCase

    @Before
    fun setUp() {
        getSeriesReviewUseCase = GetSeriesReviewUseCase(seriesDetailsRepository)
    }

    @Test
    fun `should return review related to media id when repository is called`() = runTest {
        val mediaId = 0L
        coEvery {
            seriesDetailsRepository.getTVShowReviews(
                mediaId,
            )
        } returns getSeriesReview()

        //when
        val result = getSeriesReviewUseCase.invoke(mediaId)
        val expected= getSeriesReview()

        // then
        Truth.assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesDetailsRepository.getTVShowReviews(mediaId) }
    }

    @Test
    fun `should return empty list when review is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery { seriesDetailsRepository.getTVShowReviews(mediaId) } returns emptyList()

        //when
        val result = getSeriesReviewUseCase.invoke(mediaId)

        //then
        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesDetailsRepository.getTVShowReviews(mediaId) }

    }

    @Test
    fun `should throw exception if seriesDetailsRepository throw exception `() = runTest {
        //give
        val mediaId = 3L
        val exception = Exception()
        coEvery { seriesDetailsRepository.getTVShowReviews(mediaId) } throws exception

        //when & then
        org.junit.jupiter.api.assertThrows<Exception> {
            getSeriesReviewUseCase.invoke(mediaId)
        }
    }


    private fun getSeriesReview(): List<Review> {

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