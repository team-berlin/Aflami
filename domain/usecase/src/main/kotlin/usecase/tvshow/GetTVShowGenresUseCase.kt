package usecase.tvshow

import com.berlin.entity.Genre
import repository.TvShowDetailsRepository

class GetTVShowGenresUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(): List<Genre> = tvShowDetailsRepository.getSeriesGenres()
}