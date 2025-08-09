package usecase.auth

import repository.AuthenticationRepository

class GetLoginStatusUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }
}