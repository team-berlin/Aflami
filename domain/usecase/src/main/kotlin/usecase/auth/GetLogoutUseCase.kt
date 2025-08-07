package usecase.auth

import repository.AuthenticationRepository

class GetLogoutUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() = authenticationRepository.logout()
}