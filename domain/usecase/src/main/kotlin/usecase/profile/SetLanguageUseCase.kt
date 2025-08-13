package usecase.profile

import com.berlin.entity.AppLanguage
import repository.SettingsRepository

class SetLanguageUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage) = settingsRepository.setLanguage(language)
}