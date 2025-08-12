package repository

import com.berlin.entity.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun getUserProfile(): UserProfile
    suspend fun saveUserLocally(userProfile: UserProfile)
    suspend fun getUserLocally(): UserProfile?
    fun observeUser(): Flow<UserProfile?>
    suspend fun refreshUserProfile()
    suspend fun clearLocalUser()
}