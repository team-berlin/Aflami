package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetMovieGameUseCase(
  private val movieRepository: MovieRepository
) {
    suspend operator fun invoke():List<Movie> = movieRepository.getMovieGame()
}