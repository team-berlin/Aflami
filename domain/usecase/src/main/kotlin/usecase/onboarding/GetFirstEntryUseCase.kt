package usecase.onboarding

import repository.AppEntryRepository
import javax.inject.Inject

class GetFirstEntryUseCase @Inject constructor(
    private val appEntryRepository: AppEntryRepository
){
    suspend operator fun invoke() = appEntryRepository.isFirstEntry()
}