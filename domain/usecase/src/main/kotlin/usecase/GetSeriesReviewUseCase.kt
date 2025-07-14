package usecase

import com.berlin.entity.Review
import repository.SeriesDetailsRepository

class GetSeriesReviewUseCase(
    private val seriesDetailsRepository: SeriesDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> = seriesDetailsRepository.getReviews(id)
}