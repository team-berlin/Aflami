package usecase.profile

import com.berlin.entity.AppLanguage
import repository.SettingsRepository

class GetLanguageUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(): AppLanguage = settingsRepository.getLanguage()
}