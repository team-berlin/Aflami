package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository
import javax.inject.Inject

class GetMovieGameUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
    suspend operator fun invoke():List<Movie> = movieRepository.getMovieGame()
}