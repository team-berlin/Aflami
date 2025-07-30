package usecase.auth

import repository.AuthenticationRepository

class IsLoggedInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }

}