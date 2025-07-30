package usecase

import com.berlin.entity.Movie
import repository.HomeRepository

class GetTopRatedMoviesUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(page: Int):List<Movie> = homeRepository.getTopRatedMovies(page)
}