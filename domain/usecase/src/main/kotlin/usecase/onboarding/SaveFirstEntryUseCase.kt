package usecase.onboarding

import repository.AppEntryRepository
import javax.inject.Inject

class SaveFirstEntryUseCase @Inject constructor(
    private val appEntryRepository: AppEntryRepository
){
    suspend operator fun invoke() = appEntryRepository.saveFirstEntry()
}