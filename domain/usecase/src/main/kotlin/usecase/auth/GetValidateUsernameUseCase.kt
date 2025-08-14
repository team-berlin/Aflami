package usecase.auth

import javax.inject.Inject

class GetValidateUsernameUseCase @Inject constructor() {
    operator fun invoke(username: String): Boolean =
        username.isNotEmpty() && !username.contains("%")
}