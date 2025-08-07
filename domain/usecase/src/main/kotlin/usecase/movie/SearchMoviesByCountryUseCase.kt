package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class SearchMoviesByCountryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<Movie> =
        movieRepository.getMoviesByCountry(query, page)
}