package usecase.auth

import repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository

) {
    operator fun invoke(userName: String, password: String) =
        authenticationRepository.login(userName,password)

}