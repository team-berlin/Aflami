package repository

import com.berlin.entity.User

interface UserRepository {
    suspend fun getUser(sessionId: String): User
    suspend fun saveUserLocally(user: User)
    suspend fun getUserLocally(): User?
    suspend fun logout()
}