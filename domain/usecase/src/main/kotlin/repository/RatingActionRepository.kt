package repository

import com.berlin.entity.RatingResult

interface RatingActionRepository {
    suspend fun rateMovie(movieId: Int, rating: Double): RatingResult
    suspend fun rateTvShow(tvId: Int, rating: Double): RatingResult
}