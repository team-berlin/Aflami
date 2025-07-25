package usecase

import com.berlin.entity.Movie
import repository.MovieRepository

class GetUpComingMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return movieRepository.getUpComingMovies()
    }
}