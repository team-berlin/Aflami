package usecase.auth

import repository.AuthenticationRepository

class GetLoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(userName: String, password: String) =
        authenticationRepository.login(userName, password)
}