package usecase.profile

import repository.SettingsRepository

class SetContentRestrictionUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(contentRestrictionLevel: String) =
        settingsRepository.setContentRestriction(contentRestrictionLevel)
}
