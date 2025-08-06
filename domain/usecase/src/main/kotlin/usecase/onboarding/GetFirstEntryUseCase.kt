package usecase.onboarding

import repository.AppEntryRepository

class GetFirstEntryUseCase (
    private val appEntryRepository: AppEntryRepository
){
    suspend operator fun invoke() = appEntryRepository.isFirstEntry()
}