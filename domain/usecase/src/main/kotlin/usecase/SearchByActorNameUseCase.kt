package usecase

import repository.SearchRepository

class SearchByActorNameUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(actorName: String, page: Int): List<Media> {
        return searchRepository.getMediaByActorName(actorName, page)
    }
}
