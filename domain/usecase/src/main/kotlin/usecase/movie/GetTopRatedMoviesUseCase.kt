package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetTopRatedMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int): List<Movie> = movieRepository.getTopRatedMovies(page)
}