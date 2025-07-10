package usecase

import com.berlin.entity.MediaTypeEntity
import repository.SearchRepository
import java.awt.PageAttributes

class SearchByActorNameUseCase(
    private val searchRepository: SearchRepository

) {
    suspend operator fun invoke(actorName: String): List<MediaTypeEntity> {
        return searchRepository.searchByActorName(actorName)
    }

}