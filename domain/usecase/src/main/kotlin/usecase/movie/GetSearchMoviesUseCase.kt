package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetSearchMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<Movie> =
        repository.searchMovie(query, page)
}