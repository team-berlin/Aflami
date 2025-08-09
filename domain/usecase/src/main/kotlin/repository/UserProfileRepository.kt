package repository

import com.berlin.entity.UserProfile

interface UserProfileRepository {
    suspend fun getUserProfile(sessionId: String): UserProfile
    suspend fun saveUserLocally(userProfile: UserProfile)
    suspend fun getUserLocally(): UserProfile?
    suspend fun refreshUserProfile()
    suspend fun clearLocalUser()
}