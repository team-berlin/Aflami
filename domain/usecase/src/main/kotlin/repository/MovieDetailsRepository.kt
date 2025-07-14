package repository

import com.berlin.entity.Review

interface MovieDetailsRepository {
    suspend fun getReviews(id: Long): List<Review>
}