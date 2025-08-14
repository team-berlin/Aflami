package usecase.auth

import kotlinx.coroutines.flow.Flow
import repository.AuthenticationRepository
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {
    operator fun invoke(): Flow<Boolean> {
        return authenticationRepository.observeLoginStatus()
    }
}