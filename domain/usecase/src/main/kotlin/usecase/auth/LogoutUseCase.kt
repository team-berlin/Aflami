package usecase.auth

import repository.AuthenticationRepository

class LogoutUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
//    operator fun invoke() =
//        authenticationRepository.logout()
}