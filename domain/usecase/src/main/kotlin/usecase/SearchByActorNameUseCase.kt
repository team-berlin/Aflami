package usecase

import com.berlin.entity.Movie
import repository.MovieRepository

class SearchByActorNameUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(actorName: String, page: Int): List<Movie> {
        return repository.getMediaByActorName(actorName, page)
    }
}
