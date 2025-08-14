package usecase.auth

import javax.inject.Inject

class GetValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean =
        password.isNotEmpty() && password.length >= 4
}