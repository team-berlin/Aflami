package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class SearchByActorNameUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(actorName: String, page: Int): List<Movie> =
        movieRepository.getMoviesByActorName(actorName, page)
}
