package usecase.auth

import com.berlin.entity.UserProfile
import repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(sessionId: String): UserProfile = repository.getUserProfile(sessionId)
}