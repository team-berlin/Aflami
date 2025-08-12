package usecase.tvshow

import com.berlin.entity.RatingResult
import repository.RatingRepository
import javax.inject.Inject

class RateTvShowUseCase @Inject constructor(
    private val ratingRepository: RatingRepository
) {
    suspend operator fun invoke(movieId: Int, rating: Double): RatingResult {
        return ratingRepository.rateTvShow(movieId, rating)
    }
}