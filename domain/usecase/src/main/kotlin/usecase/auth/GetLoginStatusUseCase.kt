package usecase.auth

import repository.AuthenticationRepository
import javax.inject.Inject

class GetLoginStatusUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }
}