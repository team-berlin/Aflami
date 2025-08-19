package usecase.profile

import kotlinx.coroutines.flow.Flow
import repository.SettingsRepository
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<String> = settingsRepository.getLanguage()
}