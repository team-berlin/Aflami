package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class SearchByCountryUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<Movie> =
        repository.getMoviesByCountry(query, page)
}