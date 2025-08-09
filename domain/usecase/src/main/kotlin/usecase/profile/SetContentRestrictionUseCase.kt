package usecase.profile

import com.berlin.entity.ContentRestriction
import repository.SettingsRepository

class SetContentRestrictionUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(contentRestrictionLevel: ContentRestriction) =
        settingsRepository.setContentRestriction(contentRestrictionLevel.name)
}
