package usecase

import com.berlin.entity.Movie
import repository.SearchRepository

class SearchByCountryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<Movie> {
        return searchRepository.getMoviesByCountry(query, page)
    }
}