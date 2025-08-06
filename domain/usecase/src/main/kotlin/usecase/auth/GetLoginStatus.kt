package usecase.auth

import repository.AuthenticationRepository

class GetLoginStatus(
    private val authenticationRepository: AuthenticationRepository

) {
    suspend operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }
}