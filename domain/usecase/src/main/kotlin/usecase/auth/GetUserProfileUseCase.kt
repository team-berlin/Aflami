package usecase.auth

import com.berlin.entity.UserProfile
import repository.UserProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(sessionId: String): UserProfile = repository.getUserProfile(sessionId)
}