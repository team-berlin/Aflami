package usecase.movie

import com.berlin.entity.RatingResult
import repository.RatingRepository
import javax.inject.Inject

class RateMovieUseCase @Inject constructor(
    private val ratingRepository: RatingRepository
) {
    suspend operator fun invoke(movieId: Int, rating: Double): RatingResult {
        return ratingRepository.rateMovie(movieId, rating)
    }
}