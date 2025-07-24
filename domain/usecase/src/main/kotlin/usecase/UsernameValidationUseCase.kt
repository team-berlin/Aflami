package usecase

class UsernameValidationUseCase {
    operator fun invoke(username: String): Boolean {
        return username.isNotEmpty() && !username.contains("%")
    }
}