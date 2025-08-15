package usecase.profile

import com.berlin.entity.AppTheme
import repository.SettingsRepository
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(theme: AppTheme) = settingsRepository.setTheme(theme)
}