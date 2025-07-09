package usecase

import com.berlin.entity.Movie
import repository.SearchRepository

class SearchByCountryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(countryName: String): List<Movie> {
        return emptyList()
    }
}