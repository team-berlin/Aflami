package usecase.auth

import repository.AuthenticationRepository

class GetLoginUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(userName: String, password: String) =
        repository.login(userName, password)
}