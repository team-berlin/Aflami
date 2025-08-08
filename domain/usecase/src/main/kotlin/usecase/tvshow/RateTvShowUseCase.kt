package usecase.tvshow

import com.berlin.entity.RatingResult
import repository.RatingActionRepository
import javax.inject.Inject

class RateTvShowUseCase @Inject constructor(
    private val ratingActionRepository: RatingActionRepository
) {
    suspend operator fun invoke(movieId: Int, rating: Double): RatingResult {
        return ratingActionRepository.rateTvShow(movieId, rating)
    }
}