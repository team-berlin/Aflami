package repository

interface AuthenticationRepository {

    fun register(email: String, userName: String, password: String)
    suspend fun requestToken(requestTokenRequestDTO: RequestTokenRequestDTO): LoginResponse
    suspend fun login(userName: String, password: String): com.berlin.repository.datasource.remote.dto.LoginResponse
    suspend fun logout()
}