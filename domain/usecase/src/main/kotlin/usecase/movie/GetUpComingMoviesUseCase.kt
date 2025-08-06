package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetUpComingMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(selectedGenres: Int): List<Movie> = movieRepository.getUpComingMovies(selectedGenres)
}