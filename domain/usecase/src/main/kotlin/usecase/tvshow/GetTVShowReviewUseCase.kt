package usecase.tvshow

import com.berlin.entity.Review
import repository.TvShowDetailsRepository

class GetTVShowReviewUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> =
        tvShowDetailsRepository.getReviews(id)
}