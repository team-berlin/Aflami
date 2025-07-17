package usecase

import com.berlin.entity.Movie
import repository.SearchRepository

class SearchByActorNameUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(actorName: String,page:Int): List<Movie> {
        return searchRepository.getMoviesByActorName(actorName,page )
    }
}
