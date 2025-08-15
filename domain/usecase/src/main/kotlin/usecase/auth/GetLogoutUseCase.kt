package usecase.auth

import repository.AuthenticationRepository
import javax.inject.Inject

class GetLogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() = authenticationRepository.logout()
}