package usecase.auth

import com.berlin.entity.auth.LoginToken
import repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(userName: String, password: String): LoginToken {
         return try {
             authenticationRepository.login(userName, password)
         }catch (e: Exception){
             throw e
         }
    }

}