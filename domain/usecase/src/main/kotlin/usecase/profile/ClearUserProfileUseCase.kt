package usecase.profile

import repository.UserProfileRepository
import javax.inject.Inject

class ClearUserProfileUseCase @Inject constructor(private val repo: UserProfileRepository) {
    suspend operator fun invoke() = repo.clearLocalUser()
}