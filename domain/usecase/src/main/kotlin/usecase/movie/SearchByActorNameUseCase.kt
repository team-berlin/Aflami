package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository
import javax.inject.Inject

class SearchByActorNameUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(actorName: String, page: Int): List<Movie> =
        movieRepository.getMoviesByActorName(actorName, page)
}
