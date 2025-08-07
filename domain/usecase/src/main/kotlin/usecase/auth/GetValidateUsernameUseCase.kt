package usecase.auth

class GetValidateUsernameUseCase {
    operator fun invoke(username: String): Boolean =
        username.isNotEmpty() && !username.contains("%")
}