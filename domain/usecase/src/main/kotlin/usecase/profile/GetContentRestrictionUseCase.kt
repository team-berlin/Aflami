package usecase.profile

import kotlinx.coroutines.flow.Flow
import repository.SettingsRepository

class GetContentRestrictionUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Flow<String?> = settingsRepository.getContentRestriction()
}
