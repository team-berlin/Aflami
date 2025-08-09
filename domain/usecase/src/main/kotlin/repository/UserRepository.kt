package repository

import com.berlin.entity.UserProfile

interface UserRepository {
    suspend fun getUserProfile(sessionId: String): UserProfile
    suspend fun saveUserLocally(userProfile: UserProfile)
    suspend fun getUserLocally(): UserProfile?
    suspend fun logout()
}