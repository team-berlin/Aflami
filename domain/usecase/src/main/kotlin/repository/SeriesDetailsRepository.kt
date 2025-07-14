package repository

import com.berlin.entity.Review

interface SeriesDetailsRepository {
    suspend fun getReviews(id: Long): List<Review>

}