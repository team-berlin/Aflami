package usecase.auth

class GetValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean =
        password.isNotEmpty() && password.length >= 4
}