package usecase.auth

import repository.AuthenticationRepository
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(userName: String, password: String) =
        authenticationRepository.login(userName, password)
}