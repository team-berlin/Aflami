package usecase.auth

import kotlinx.coroutines.flow.Flow
import repository.AuthenticationRepository

class GetLoginStatus(
    private val authenticationRepository: AuthenticationRepository,

    ) {
    operator fun invoke(): Flow<Boolean> {
        return authenticationRepository.observeLoginStatus()
    }
}