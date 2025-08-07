package repository

import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun observeLoginStatus(): Flow<Boolean>
    suspend fun isLoggedIn(): Boolean
    suspend fun login(userName: String, password: String)
    suspend fun logout()
}