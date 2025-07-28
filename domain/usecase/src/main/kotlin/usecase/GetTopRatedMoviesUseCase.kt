package usecase

import com.berlin.entity.Movie
import repository.MovieRepository

class GetTopRatedMoviesUseCase(private val homeRepository: MovieRepository) {
    suspend operator fun invoke(page: Int):List<Movie> = homeRepository.getTopRatedMovies(page)
}