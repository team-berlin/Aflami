package usecase.onboarding

import repository.AppEntryRepository

class IsFirstEntryUseCase (
    private val appEntryRepository: AppEntryRepository
){
    suspend operator fun invoke() = appEntryRepository.isFirstEntry()
}