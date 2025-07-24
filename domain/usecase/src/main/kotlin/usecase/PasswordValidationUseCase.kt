package usecase

class PasswordValidationUseCase {
    operator fun invoke(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 4
    }
}