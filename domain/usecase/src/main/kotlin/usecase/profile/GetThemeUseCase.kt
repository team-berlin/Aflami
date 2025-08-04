package usecase.profile

import com.berlin.entity.AppTheme
import repository.SettingsRepository

class GetThemeUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(): AppTheme = settingsRepository.getTheme()
}