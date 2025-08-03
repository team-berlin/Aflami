package usecase.tvshow

import com.berlin.entity.Genre
import repository.TvShowDetailsRepository
import javax.inject.Inject

class GetTVShowGenresUseCase @Inject constructor(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(): List<Genre> = tvShowDetailsRepository.getSeriesGenres()
}