package usecase.onboarding

import repository.AppEntryRepository

class SaveFirstEntryUseCase (
    private val appEntryRepository: AppEntryRepository
){
    suspend operator fun invoke() = appEntryRepository.saveFirstEntry()
}