package usecase.profile

import kotlinx.coroutines.flow.Flow
import repository.SettingsRepository

class GetThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Flow<String?> = settingsRepository.getTheme()
}