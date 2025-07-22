package usecase.auth

import com.berlin.entity.auth.LoginToken
import com.berlin.entity.auth.Session
import repository.AuthenticationRepository

class CreateSessionUseCase(
    private val authenticationRepository: AuthenticationRepository,
 ) {
    suspend operator fun invoke(requestToken:String): Session {
        return authenticationRepository.createSession(requestToken)
    }

}