package usecase

import com.berlin.entity.Genre
import repository.TVShowDetailsRepository

class GetSeriesGenresUseCase(private val tvShowDetailsRepository: TVShowDetailsRepository) {
    suspend operator fun invoke(language: String): List<Genre> {
        return tvShowDetailsRepository.getTVShowGenres(language)
    }
}
