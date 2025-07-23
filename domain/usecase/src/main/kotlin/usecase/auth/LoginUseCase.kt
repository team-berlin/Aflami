package usecase.auth

import com.berlin.entity.auth.LoginToken
import repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(userName: String, password: String): LoginToken {
        val userToken = getUserToken()
        return authenticationRepository.login(userName, password, userToken)
    }


    private suspend fun getUserToken(): String {
        try {
            val requestTokenResult = authenticationRepository.requestToken()
            return if (requestTokenResult.success) requestTokenResult.requestToken
            else throw Exception("Failed to get request token")
        }catch (e:Exception){
            throw e
        }
    }
}