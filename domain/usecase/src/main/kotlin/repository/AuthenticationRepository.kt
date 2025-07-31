package repository


interface AuthenticationRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun login(userName: String, password: String)
    suspend fun logout()
}