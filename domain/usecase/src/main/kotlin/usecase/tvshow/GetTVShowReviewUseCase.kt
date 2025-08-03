package usecase.tvshow

import com.berlin.entity.Review
import repository.TvShowDetailsRepository
import javax.inject.Inject

class GetTVShowReviewUseCase @Inject constructor(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> =
        tvShowDetailsRepository.getReviews(id)
}