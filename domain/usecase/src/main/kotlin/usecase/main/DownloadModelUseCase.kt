package usecase.main

class DownloadModelsUseCase(
    private val repository: ModelRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.downloadAllModelsOnce()
    }
}