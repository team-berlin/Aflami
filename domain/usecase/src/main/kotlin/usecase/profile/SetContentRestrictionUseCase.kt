package usecase.profile

import com.berlin.entity.ContentRestriction
import repository.SettingsRepository
import javax.inject.Inject

class SetContentRestrictionUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(contentRestrictionLevel: ContentRestriction) =
        settingsRepository.setContentRestriction(contentRestrictionLevel.name)
}
