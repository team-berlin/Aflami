package usecase.profile

import com.berlin.entity.AppTheme
import repository.SettingsRepository

class SetThemeUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(theme: AppTheme) = settingsRepository.setTheme(theme)
}