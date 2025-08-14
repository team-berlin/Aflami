package usecase.profile

import com.berlin.entity.AppLanguage
import repository.SettingsRepository
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage) = settingsRepository.setLanguage(language)
}