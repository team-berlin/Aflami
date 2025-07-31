package usecase.auth

import repository.AuthenticationRepository

class GetLogoutUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke() = repository.logout()
}