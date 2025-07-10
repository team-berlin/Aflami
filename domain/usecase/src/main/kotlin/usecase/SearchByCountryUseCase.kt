package usecase

import com.berlin.entity.Movie
import repository.SearchRepository

class SearchByCountryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(countryName: String, language: String): List<Movie> {
        return searchRepository.searchByCountry(countryName, language)
    }
}