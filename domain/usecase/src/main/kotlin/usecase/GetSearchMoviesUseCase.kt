package usecase

import com.berlin.entity.Movie
import repository.SearchRepository

class GetSearchMoviesUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<Movie> = searchRepository.searchMovie(query)
}