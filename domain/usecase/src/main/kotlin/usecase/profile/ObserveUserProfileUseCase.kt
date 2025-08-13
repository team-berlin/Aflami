package usecase.profile

import repository.UserProfileRepository
import javax.inject.Inject

class ObserveUserProfileUseCase @Inject constructor(private val repo: UserProfileRepository) {
    operator fun invoke() = repo.observeUser()
}