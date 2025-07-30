package usecase.main

import repository.ModelRepository

class DownloadModelsUseCase(
    private val repository: ModelRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.downloadAllModelsOnce()
    }
}