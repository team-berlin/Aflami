package usecase.mediadetails

import com.berlin.entity.Review
import repository.TvShowDetailsRepository

class GetSeriesReviewUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> = tvShowDetailsRepository.getReviews(id)
}