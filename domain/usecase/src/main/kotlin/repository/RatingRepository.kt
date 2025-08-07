package repository

import com.berlin.entity.RatingResult

interface RatingRepository {
    suspend fun rateMovie(movieId: Int, rating: Double, sessionId: String): RatingResult
    suspend fun rateTvShow(tvId: Int, rating: Double, sessionId: String): RatingResult
}