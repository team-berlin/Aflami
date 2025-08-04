package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetSearchMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<Movie> =
        movieRepository.getMovieByKeyWord(query, page)
}