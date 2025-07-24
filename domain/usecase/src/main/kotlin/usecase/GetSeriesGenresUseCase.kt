package usecase

import com.berlin.entity.Genre
import repository.TvShowDetailsRepository

class GetSeriesGenresUseCase(private val tvShowDetailsRepository: TvShowDetailsRepository) {
    suspend operator fun invoke(language: String): List<Genre> {
        return tvShowDetailsRepository.getSeriesGenres(language)
    }
}
