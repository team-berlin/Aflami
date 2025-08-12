package usecase.profile

import repository.UserProfileRepository
import javax.inject.Inject

class RefreshUserProfileUseCase @Inject constructor(private val repo: UserProfileRepository) {
    suspend operator fun invoke() = repo.refreshUserProfile()
}