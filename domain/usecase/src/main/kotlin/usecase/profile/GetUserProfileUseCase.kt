package usecase.profile

import com.berlin.entity.UserProfile
import repository.UserProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(): UserProfile = repository.getUserProfile()
}